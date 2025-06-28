package com.jeanmeza.dispensapp.ui.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun ItemDialog(
    onDismiss: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: ItemScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.resetToFreshState()
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
        ),
    ) {
        val uiState by viewModel.itemUiState.collectAsStateWithLifecycle()
        val coroutineScope = rememberCoroutineScope()
        ItemScreen(
            onShowSnackbar = onShowSnackbar,
            isEditing = viewModel.isEditing,
            item = uiState.item,
            quantityInput = uiState.quantityInput,
            onNameChange = viewModel::onNameChange,
            nameHasError = uiState.nameHasError,
            measureUnitHasError = uiState.measureUnitHasError,
            quantityHasError = uiState.quantityHasError,
            onMeasureUnitChange = viewModel::onMeasureUnitChange,
            onExpiryDateChange = viewModel::onExpiryDateChange,
            onQuantityChange = viewModel::onQuantityChange,
            onCategoriesChange = viewModel::onCategoriesChange,
            onImageUriChange = viewModel::onImageUriChange,
            onSaveClicked = viewModel::onSaveClicked,
            onDeleteClicked = {
                coroutineScope.launch {
                    viewModel.onDeleteClicked()
                    onDismiss()
                    onShowSnackbar("Item deleted", null)
                }
            },
            onBackClicked = onDismiss,
            coroutineScope = coroutineScope,
        )
    }
}