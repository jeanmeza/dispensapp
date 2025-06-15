package com.jeanmeza.dispensapp.ui.categoryselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanmeza.dispensapp.data.local.entities.asModel
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class CategorySelectionViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    val uiState: StateFlow<CategorySelectionUiState> =
        categoryRepository.getCategoriesStream().map {
            CategorySelectionUiState(
                categories = it.asModel(),
                selectedCategories = emptyList()
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = CategorySelectionUiState.new()
            )

}

data class CategorySelectionUiState(
    val categories: List<Category>,
    val selectedCategories: List<Category>,
) {
    companion object {
        fun new() = CategorySelectionUiState(
            categories = emptyList(),
            selectedCategories = emptyList(),
        )
    }
}
