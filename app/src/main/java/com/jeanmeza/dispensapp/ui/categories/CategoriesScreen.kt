package com.jeanmeza.dispensapp.ui.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.ui.component.CategoryCard
import com.jeanmeza.dispensapp.ui.previewprovider.CategoryScreenPreviewParameterProvider
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme

@Composable
fun CategoriesRoute(
    modifier: Modifier = Modifier,
    onSelectionStart: () -> Unit,
    onSelectionEnd: () -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CategoriesScreen(
        modifier = modifier,
        categories = uiState.categories,
        isSelectingCategories = uiState.isSelectingCategories,
        toggleCategorySelection = {
            viewModel.toggleCategorySelection(it, onSelectionEnd)
        },
        activateSelection = {
            viewModel.activateSelection(it)
            onSelectionStart()
        },
        cancelSelection = {
            viewModel.cancelSelection()
            onSelectionEnd()
        },
        selectAll = viewModel::selectAll,
        deleteSelected = {
            viewModel.deleteSelected()
            onSelectionEnd()
        },
        isSelected = viewModel::isCategorySelected,
        amountSelected = uiState.selectedCategories.size,
    )
}

@Composable
private fun CategoriesScreen(
    categories: List<Category>,
    isSelectingCategories: Boolean,
    toggleCategorySelection: (Category) -> Unit,
    activateSelection: (Category) -> Unit,
    cancelSelection: () -> Unit,
    selectAll: () -> Unit,
    deleteSelected: () -> Unit,
    isSelected: (Category) -> Boolean,
    amountSelected: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
    ) {
        if (isSelectingCategories) {
            CategorySelectionTopBar(
                selected = amountSelected,
                onCancelClick = cancelSelection,
                onSelectAllClick = selectAll,
                onDeleteClick = deleteSelected,
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.p_md)),
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

@Composable
private fun CategorySelectionTopBar(
    modifier: Modifier = Modifier,
    selected: Int,
    onCancelClick: () -> Unit,
    onSelectAllClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text("$selected categories")
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onCancelClick) {
                Icon(
                    imageVector = DispensAppIcons.CloseOutlined,
                    contentDescription = stringResource(R.string.cancel),
                )
            }
        },
        actions = {
            IconButton(onClick = onSelectAllClick) {
                Icon(
                    imageVector = DispensAppIcons.SelectAll,
                    contentDescription = stringResource(R.string.select_all),
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = DispensAppIcons.DeleteOutlined,
                    contentDescription = stringResource(R.string.delete),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CategoriesScreenPreview(
    @PreviewParameter(CategoryScreenPreviewParameterProvider::class)
    categories: List<Category>,
) {
    DispensAppTheme {
        CategoriesScreen(
            categories = categories,
            isSelectingCategories = false,
            toggleCategorySelection = {},
            activateSelection = {},
            cancelSelection = {},
            selectAll = {},
            deleteSelected = {},
            isSelected = { false },
            amountSelected = 0
        )
    }
}
