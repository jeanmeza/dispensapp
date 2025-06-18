package com.jeanmeza.dispensapp.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanmeza.dispensapp.data.local.entities.asModel
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    categoryRepository: CategoryRepository
) : ViewModel() {
    private val _categoriesFlow = categoryRepository.getCategoriesStream()
    private val _isSelectingCategoriesFlow = MutableStateFlow(false)
    private val _selectedCategoriesFlow = MutableStateFlow(emptySet<Category>())

    val uiState: StateFlow<CategoriesUiState> =
        combine(
            _categoriesFlow,
            _isSelectingCategoriesFlow,
            _selectedCategoriesFlow
        ) { categories, isSelectingCategories, selectedCategories ->
            CategoriesUiState(
                categories = categories.asModel(),
                isSelectingCategories = isSelectingCategories,
                selectedCategories = selectedCategories,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = CategoriesUiState.new()
        )

    fun toggleCategorySelection(category: Category) {
        _selectedCategoriesFlow.getAndUpdate {
            val sc = it.toMutableSet()
            if (sc.contains(category)) {
                sc.remove(category)
            } else {
                sc.add(category)
            }
            sc.toSet()
        }
        if (_selectedCategoriesFlow.value.isEmpty()) {
            setIsSelectingCategories(false)
        }
    }

    fun setIsSelectingCategories(isSelectingCategories: Boolean) {
        _isSelectingCategoriesFlow.update { isSelectingCategories }
    }

    fun activateSelection(category: Category) {
        setIsSelectingCategories(true)
        toggleCategorySelection(category)
    }

    fun cancelCategorySelection() {
        setIsSelectingCategories(false)
        _selectedCategoriesFlow.update { emptySet() }
    }
}

data class CategoriesUiState(
    val categories: List<Category>,
    val isSelectingCategories: Boolean,
    val selectedCategories: Set<Category>,
) {
    companion object {
        fun new() = CategoriesUiState(
            categories = emptyList(),
            isSelectingCategories = false,
            selectedCategories = emptySet(),
        )
    }
}
