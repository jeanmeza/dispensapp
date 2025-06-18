package com.jeanmeza.dispensapp.ui.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.ui.component.CategoryCard
import com.jeanmeza.dispensapp.ui.previewprovider.CategoryScreenPreviewParameterProvider
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme

@Composable
fun CategoriesRoute(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CategoriesScreen(
        modifier = modifier,
        categories = uiState.categories,
        isSelectingCategories = uiState.isSelectingCategories,
        toggleCategorySelection = viewModel::toggleCategorySelection,
        activateSelection = viewModel::activateSelection,
        isSelected = viewModel::isCategorySelected,
    )
}

@Composable
private fun CategoriesScreen(
    categories: List<Category>,
    isSelectingCategories: Boolean,
    toggleCategorySelection: (Category) -> Unit,
    activateSelection: (Category) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: (Category) -> Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.p_md))
            .background(color = MaterialTheme.colorScheme.surface),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_md)),
        ) {
            items(items = categories, key = { it.id }) { category ->
                CategoryCard(
                    category = category,
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {
                                if (isSelectingCategories) {
                                    toggleCategorySelection(category)
                                } else {
                                    // TODO: Navigate to items screen filtering by category
                                }
                            },
                            onLongClick = {
                                if (!isSelectingCategories) {
                                    activateSelection(category)
                                }
                            }
                        ),
                    selected = isSelected(category),
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CategoriesScreenPreview(
    @PreviewParameter(CategoryScreenPreviewParameterProvider::class)
    categories: List<Category>,
) {
    DispensAppTheme {
        CategoriesScreen(
            modifier = Modifier.statusBarsPadding(),
            categories = categories,
            isSelectingCategories = false,
            toggleCategorySelection = {},
            activateSelection = {},
            isSelected = { false },
        )
    }
}
