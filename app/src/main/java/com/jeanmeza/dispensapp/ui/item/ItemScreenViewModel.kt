package com.jeanmeza.dispensapp.ui.item

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import javax.inject.Inject

const val TAG = "ItemScreenViewModel"

@HiltViewModel
class ItemScreenViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val itemRoute: ItemRoute = savedStateHandle.toRoute()
    private val itemId: Int? = itemRoute.itemId

    val isEditing = itemId != null

    private val _itemUiState = MutableStateFlow(ItemScreenUiState.new())
    val itemUiState = _itemUiState.asStateFlow()

    init {
        if (isEditing) {
            loadItemForEditing(itemId!!)
        }
    }

    private fun loadItemForEditing(itemId: Int) {
        viewModelScope.launch {
            val item = itemRepository.getItem(itemId)
            _itemUiState.update {
                ItemScreenUiState(item.asModel())
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
        val itemToSave = currentItem.copy(quantity = quantity!!)

        Log.d(TAG, "onSaveClicked: $itemToSave")
        itemRepository.upsert(itemToSave.asEntity())

        itemToSave.categoriesCrossRef().flatten()
        // TODO: save itemcategoriescrossref

        return true
    }

    fun onDeleteClicked() {
        viewModelScope.launch {
            itemRepository.delete(_itemUiState.value.item.asEntity())
        }
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
            )
            return ItemScreenUiState(
                item = item,
                quantityInput = item.quantity.toString(),
            )
        }
    }
}
