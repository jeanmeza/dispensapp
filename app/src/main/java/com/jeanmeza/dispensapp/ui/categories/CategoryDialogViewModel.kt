package com.jeanmeza.dispensapp.ui.categories

import androidx.lifecycle.ViewModel
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.model.asEntity
import com.jeanmeza.dispensapp.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CategoryDialogViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _categoryDialogUiState = MutableStateFlow(CategoryDialogUiState.new())
    val categoryDialogUiState = _categoryDialogUiState.asStateFlow()

    fun onNameChange(name: String) {
        _categoryDialogUiState.value = _categoryDialogUiState.value.copy(
            category = _categoryDialogUiState.value.category.copy(name = name)
        )
    }

    suspend fun onSaveClick(): Boolean {
        val currentCategory = _categoryDialogUiState.value.category
        _categoryDialogUiState.update {
            it.copy(
                nameHasError = currentCategory.name.isBlank()
            )
        }
        if (_categoryDialogUiState.value.nameHasError) {
            return false
        }
        categoryRepository.insert(currentCategory.asEntity())
        return true
    }
}

data class CategoryDialogUiState(
    val category: Category,
    var nameHasError: Boolean = false,
) {
    companion object {
        fun new(): CategoryDialogUiState {
            return CategoryDialogUiState(Category(id = 0, name = ""))
        }
    }
}