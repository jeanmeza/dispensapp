package com.jeanmeza.dispensapp.ui.categoryselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.ui.categories.CategoryDialog
import com.jeanmeza.dispensapp.ui.previewprovider.CategorySelectionPreviewParameterProvider
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import com.jeanmeza.dispensapp.ui.theme.Shapes

@Composable
fun CategorySelectionDialog(
    onDismiss: () -> Unit,
    currentCategories: List<Category>,
    onCategoryChange: (Category) -> Unit,
    onSave: () -> Unit,
    viewModel: CategorySelectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showCategoryDialog by rememberSaveable { mutableStateOf(false) }

    if (showCategoryDialog) {
        CategoryDialog(onDismiss = { showCategoryDialog = false })
    }

    CategorySelectionDialog(
        onDismiss = onDismiss,
        onCategorySelection = onCategoryChange,
        onSave = onSave,
        onCreateCategory = { showCategoryDialog = true },
        categories = uiState.categories,
        selectedCategories = currentCategories
    )
}

@Composable
private fun CategorySelectionDialog(
    onDismiss: () -> Unit,
    onCategorySelection: (Category) -> Unit,
    onSave: () -> Unit,
    onCreateCategory: () -> Unit,
    categories: List<Category>,
    selectedCategories: List<Category>,
) {
    val configuration = LocalConfiguration.current
    /**
     * usePlatformDefaultWidth = false is use as a temporary fix to allow
     * height recalculation during recomposition. This, however, causes
     * Dialog's to occupy full width in Compact mode. Therefore max width
     * is configured below. This should be removed when there's fix to
     * https://issuetracker.google.com/issues/221643630
     */
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.select_categories),
                style = MaterialTheme.typography.titleLarge,
            )
        },

        text = {
            LazyColumn {
                items(items = categories, key = { it.id }) { category ->
                    Card(
                        onClick = { onCategorySelection(category) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = Shapes.extraSmall,
                        colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = dimensionResource(R.dimen.p_sm),
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = DispensAppIcons.LabelOutlined,
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.widthIn(dimensionResource(R.dimen.p_sm)))
                            Text(text = category.name)
                            Spacer(modifier = Modifier.weight(1f))
                            Checkbox(
                                checked = isCategorySelected(category, selectedCategories),
                                onCheckedChange = null
                            )
                        }
                    }
                }

                item {
                    Card(
                        onClick = onCreateCategory,
                        modifier = Modifier.fillMaxWidth(),
                        shape = Shapes.extraSmall,
                        colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = dimensionResource(R.dimen.p_sm),
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = DispensAppIcons.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                            Spacer(modifier = Modifier.widthIn(dimensionResource(R.dimen.p_sm)))
                            Text(
                                text = stringResource(R.string.new_category),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        }
                    }
                }
            }
        },

        confirmButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.p_sm)),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            ) {
                Text(stringResource(R.string.cancel))
            }
            TextButton(
                onClick = onSave,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.p_sm)),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            ) {
                Text(stringResource(R.string.save))
            }
        }
    )
}

@Composable
fun isCategorySelected(category: Category, categories: List<Category>): Boolean {
    return categories.contains(category)
}

@Preview(apiLevel = 35, showBackground = true)
@Composable
fun CategorySelectionDialogPreview(
    @PreviewParameter(provider = CategorySelectionPreviewParameterProvider::class)
    categories: List<Category>,
) {
    DispensAppTheme {
        CategorySelectionDialog(
            onDismiss = {},
            onCategorySelection = {},
            onSave = {},
            onCreateCategory = {},
            categories = categories,
            selectedCategories = emptyList(),
        )
    }
}
