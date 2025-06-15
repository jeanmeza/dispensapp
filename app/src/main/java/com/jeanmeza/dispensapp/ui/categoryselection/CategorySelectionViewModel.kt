package com.jeanmeza.dispensapp.ui.categoryselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanmeza.dispensapp.data.local.entities.asModel
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySelectionViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategorySelectionUiState.new())
    val uiState: StateFlow<CategorySelectionUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            categoryRepository.getCategoriesStream().collect { categories ->
                _uiState.update { currentState ->
                    currentState.copy(categories = categories.asModel())
                }
            }
        }
    }

    fun setInitialSelectedCategories(categories: List<Category>) {
        _uiState.update { currentState ->
            currentState.copy(selectedCategories = categories)
        }
    }

    fun toggleCategorySelection(category: Category) {
        _uiState.update { currentState ->
            val currentSelected = currentState.selectedCategories.toMutableList()
            if (currentSelected.contains(category)) {
                currentSelected.remove(category)
            } else {
                currentSelected.add(category)
            }
            currentState.copy(selectedCategories = currentSelected.toList())
        }
    }

    fun getCurrentSelectedCategories(): List<Category> {
        return _uiState.value.selectedCategories
    }
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
