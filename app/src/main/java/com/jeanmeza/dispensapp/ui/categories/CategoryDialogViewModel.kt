package com.jeanmeza.dispensapp.ui.categories

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.ViewModel
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.model.asEntity
import com.jeanmeza.dispensapp.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val TAG = "CategoryDialogViewModel"

@HiltViewModel
class CategoryDialogViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _categoryDialogUiState = MutableStateFlow(CategoryDialogUiState.new())
    val categoryDialogUiState = _categoryDialogUiState.asStateFlow()

    fun resetUiState() {
        _categoryDialogUiState.value = CategoryDialogUiState.new()
    }

    fun onNameChange(name: String) {
        _categoryDialogUiState.value = _categoryDialogUiState.value.copy(
            category = _categoryDialogUiState.value.category.copy(name = name),
            nameHasError = false,
            nameErrorMessage = "",
        )
    }

    suspend fun onSaveClick(): Boolean {
        val currentCategory = _categoryDialogUiState.value.category
        if (currentCategory.name.isBlank()) {
            _categoryDialogUiState.update {
                it.copy(
                    nameHasError = true,
                    nameErrorMessage = "The name cannot be empty"
                )
            }
            return false
        }
        return try {
            categoryRepository.insert(currentCategory.asEntity())
            _categoryDialogUiState.update {
                it.copy(
                    nameHasError = false,
                    nameErrorMessage = "",
                )
            }
            true
        } catch (e: SQLiteConstraintException) {
            Log.e(TAG, "Error saving category", e)
            _categoryDialogUiState.update {
                it.copy(
                    nameHasError = true,
                    nameErrorMessage = "That category already exists",
                )
            }
            false
        } catch (e: Exception) {
            Log.e(TAG, "Error saving category", e)
            false
        }
    }
}

data class CategoryDialogUiState(
    val category: Category,
    var nameHasError: Boolean,
    var nameErrorMessage: String,
) {
    companion object {
        fun new(): CategoryDialogUiState {
            return CategoryDialogUiState(
                category = Category(id = 0, name = ""),
                nameHasError = false,
                nameErrorMessage = "",
            )
        }
    }
}