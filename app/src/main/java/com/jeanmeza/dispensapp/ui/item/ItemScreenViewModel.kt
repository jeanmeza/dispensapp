package com.jeanmeza.dispensapp.ui.item

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jeanmeza.dispensapp.data.local.entities.asModel
import com.jeanmeza.dispensapp.data.local.entities.categoriesCrossRef
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.data.model.asEntity
import com.jeanmeza.dispensapp.data.repository.ItemRepository
import com.jeanmeza.dispensapp.ui.item.navigation.ItemRoute
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Instant

const val TAG = "ItemScreenViewModel"

@HiltViewModel(assistedFactory = ItemScreenViewModel.Factory::class)
class ItemScreenViewModel @AssistedInject constructor(
    private val itemRepository: ItemRepository,
    savedStateHandle: SavedStateHandle,
    @Assisted assistedItemId: Int?,
    @Assisted assistedItemName: String?,
) : ViewModel() {

    private val itemId: Int? = assistedItemId ?: try {
        savedStateHandle.toRoute<ItemRoute>().itemId
    } catch (_: Exception) {
        null
    }

    val isEditing = itemId != null

    private val _itemUiState = MutableStateFlow(ItemScreenUiState.new())
    val itemUiState = _itemUiState.asStateFlow()

    init {
        if (isEditing) {
            loadItemForEditing(itemId!!)
        } else {
            if (assistedItemName != null)
                _itemUiState.update {
                    it.copy(item = it.item.copy(name = assistedItemName))
                }
        }
    }

    fun resetToFreshState() {
        if (isEditing) {
            loadItemForEditing(itemId!!)
        } else {
            ItemScreenUiState.new()
        }
    }

    private fun loadItemForEditing(itemId: Int) {
        viewModelScope.launch {
            itemRepository.getItemStream(itemId).collect { populatedItem ->
                _itemUiState.update {
                    ItemScreenUiState(populatedItem.asModel())
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _itemUiState.update {
            it.copy(item = it.item.copy(name = name))
        }
    }

    fun onMeasureUnitChange(measureUnit: String) {
        _itemUiState.update {
            it.copy(item = it.item.copy(measureUnit = measureUnit))
        }
    }

    fun onExpiryDateChange(instant: Instant?) {
        _itemUiState.update {
            it.copy(item = it.item.copy(expiryDate = instant))
        }
    }

    fun onQuantityChange(quantity: String) {
        val digits = quantity.filter { it.isDigit() }
        _itemUiState.update {
            it.copy(quantityInput = digits)
        }
    }

    fun onCategoriesChange(categories: List<Category>) {
        _itemUiState.update {
            it.copy(item = it.item.copy(categories = categories))
        }
    }

    fun onImageUriChange(imageUri: Uri?) {
        _itemUiState.update {
            it.copy(item = it.item.copy(imageUri = imageUri))
        }
    }

    suspend fun onSaveClicked(): Boolean {
        val currentItem = _itemUiState.value.item
        val quantity = _itemUiState.value.quantityInput.toIntOrNull()
        _itemUiState.update {
            it.copy(
                nameHasError = currentItem.name.isBlank(),
                measureUnitHasError = currentItem.measureUnit.isBlank(),
                quantityHasError = quantity == null || quantity <= 0
            )
        }
        if (_itemUiState.value.nameHasError
            || _itemUiState.value.measureUnitHasError
            || _itemUiState.value.quantityHasError
        ) {
            return false
        }

        var itemToSave = currentItem.copy(quantity = quantity!!)

        try {
            Log.d(TAG, "onSaveClicked: $itemToSave")

            val newId = itemRepository.upsert(itemToSave.asEntity()).toInt()
            val itemId = if (newId > 0) newId else itemToSave.id
            itemToSave = itemToSave.copy(id = itemId)

            val categoryCrossRefs = itemToSave.categoriesCrossRef()
            val categoryIds = categoryCrossRefs.map { it.categoryId }

            itemRepository.deleteCategoriesCrossRef(itemId, categoryIds)
            itemRepository.insertItemCategoryCrossRef(categoryCrossRefs)

            return true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save item $itemToSave", e)
            return false
        }
    }

    fun onDeleteClicked() {
        viewModelScope.launch {
            itemRepository.delete(_itemUiState.value.item.asEntity())
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(itemId: Int?, itemName: String?): ItemScreenViewModel
    }
}

data class ItemScreenUiState(
    val item: Item,
    val quantityInput: String = item.quantity.toString(),
    var nameHasError: Boolean = false,
    var measureUnitHasError: Boolean = false,
    var quantityHasError: Boolean = false,
) {
    companion object {
        fun new(): ItemScreenUiState {
            val item = Item(
                id = 0,
                name = "",
                quantity = 1,
                measureUnit = "",
                expiryDate = null,
                categories = emptyList(),
                imageUri = null,
            )
            return ItemScreenUiState(
                item = item,
                quantityInput = item.quantity.toString(),
            )
        }
    }
}