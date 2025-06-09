package com.jeanmeza.dispensapp.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanmeza.dispensapp.data.local.entities.asExternalModel
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
class CategoriesViewModel @Inject constructor(
    categoryRepository: CategoryRepository
) : ViewModel() {
    val categoriesUiState: StateFlow<CategoriesUiState> =
        categoryRepository.getCategoriesStream().map { CategoriesUiState(it.asExternalModel()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = CategoriesUiState()
            )
}

data class CategoriesUiState(
    val categories: List<Category> = emptyList()
)