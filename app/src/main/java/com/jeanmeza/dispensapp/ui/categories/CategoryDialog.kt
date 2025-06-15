package com.jeanmeza.dispensapp.ui.categories

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CategoryDialog(
    onDismiss: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: CategoryDialogViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    val uiState by viewModel.categoryDialogUiState.collectAsStateWithLifecycle()
    CategoryDialog(
        category = uiState.category,
        onDismiss = onDismiss,
        nameHasError = uiState.nameHasError,
        onNameChange = viewModel::onNameChange,
        onSaveClick = viewModel::onSaveClick,
        coroutineScope = coroutineScope,
        onShowSnackbar = onShowSnackbar,
    )
}

@Composable
fun CategoryDialog(
    category: Category,
    onDismiss: () -> Unit,
    nameHasError: Boolean,
    onNameChange: (String) -> Unit,
    onSaveClick: suspend () -> Boolean,
    coroutineScope: CoroutineScope,
    onShowSnackbar: suspend (String, String?) -> Boolean,
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
                text = stringResource(R.string.create_category),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            OutlinedTextField(
                value = category.name,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.name)) },
                isError = nameHasError,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
            )
        },
        confirmButton = {
            Row {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.p_sm)),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground,
                    )
                ) {
                    Text(stringResource(R.string.cancel))
                }
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            if (onSaveClick()) {
                                onDismiss()
                                onShowSnackbar("Category saved", null)
                            }
                        }
                    },
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.p_sm)),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground,
                    )
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    )
}


@Preview(apiLevel = 35, showBackground = true)
@Composable
fun CategoryDialogPreview() {
    DispensAppTheme {
        CategoryDialog(
            onDismiss = {},
            category = Category(id = 0, name = "Some name"),
            nameHasError = false,
            onNameChange = {},
            onSaveClick = { true },
            coroutineScope = rememberCoroutineScope(),
            onShowSnackbar = { _, _ -> true },
        )
    }
}