package com.jeanmeza.dispensapp.ui.categoryitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanmeza.dispensapp.data.local.entities.asModel
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.repository.CategoryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.seconds

@HiltViewModel(assistedFactory = CategoryItemsViewModel.Factory::class)
class CategoryItemsViewModel @AssistedInject constructor(
    categoryRepository: CategoryRepository,
    @Assisted private val categoryId: Int,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(categoryId: Int): CategoryItemsViewModel
    }

    val uiState = categoryRepository.getCategoriesWithItemsStream(categoryId).map {
        CategoryItemsUiState(category = it.category.asModel())
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