package com.jeanmeza.dispensapp.ui.item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jeanmeza.dispensapp.data.local.entities.asExternalModel
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.data.repository.ItemRepository
import com.jeanmeza.dispensapp.ui.item.navigation.ItemRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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
