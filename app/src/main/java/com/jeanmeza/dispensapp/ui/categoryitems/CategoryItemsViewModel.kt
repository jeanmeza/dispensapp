package com.jeanmeza.dispensapp.ui.categoryitems

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jeanmeza.dispensapp.data.local.entities.asModel
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.repository.CategoryRepository
import com.jeanmeza.dispensapp.ui.categoryitems.navigation.CategoryItemsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class CategoryItemsViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
    saveStateHandle: SavedStateHandle,
) : ViewModel() {

    val categoryId: Int = saveStateHandle.toRoute<CategoryItemsRoute>().categoryId

    val uiState = categoryRepository.getCategoriesWithItemsStream(categoryId).map {
        CategoryItemsUiState(category = it.asModel())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
        initialValue = CategoryItemsUiState.new()
    )

}

data class CategoryItemsUiState(
    val category: Category,
) {
    companion object {
        fun new() = CategoryItemsUiState(
            category = Category(id = 0, name = ""),
        )
    }
}