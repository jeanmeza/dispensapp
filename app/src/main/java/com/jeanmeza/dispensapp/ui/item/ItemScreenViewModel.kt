package com.jeanmeza.dispensapp.ui.item

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jeanmeza.dispensapp.data.local.entities.asExternalModel
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.data.model.asEntity
import com.jeanmeza.dispensapp.data.repository.ItemRepository
import com.jeanmeza.dispensapp.ui.item.navigation.ItemRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

const val TAG = "ItemScreenViewModel"

@HiltViewModel
class ItemScreenViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val savedStateHandle: SavedStateHandle,
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
                ItemScreenUiState(item.asExternalModel())
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

    fun onExpiryDateChange(millis: Long?) {
        _itemUiState.update {
            if (millis == null) {
                return@update it.copy(item = it.item.copy(expiryDate = null))
            }
            val localDate =
                Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
            it.copy(item = it.item.copy(expiryDate = localDate))
        }
    }

    fun onQuantityChange(quantity: String) {
        _itemUiState.update {
            // FIXME: breaks when the user deletes the number
            it.copy(item = it.item.copy(quantity = quantity.toInt()))
        }
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            val item = itemUiState.value.item
            Log.d(TAG, "onSaveClicked: $item")
            itemRepository.upsert(item.asEntity())
        }
    }
}

data class ItemScreenUiState(
    val item: Item
) {
    companion object {
        fun new(): ItemScreenUiState = ItemScreenUiState(
            Item(
                id = 0,
                categoryId = null,
                name = "",
                quantity = 1,
                measureUnit = "",
                expiryDate = null,
            )
        )
    }
}
