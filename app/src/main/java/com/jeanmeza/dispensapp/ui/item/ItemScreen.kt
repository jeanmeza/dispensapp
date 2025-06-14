package com.jeanmeza.dispensapp.ui.item

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.formattedDate
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant

@Composable
fun ItemRoute(
    onBackClicked: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: ItemScreenViewModel = hiltViewModel(),
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
        onSaveClicked = viewModel::onSaveClicked,
        onDeleteClicked = {
            coroutineScope.launch {
                viewModel.onDeleteClicked()
                onBackClicked()
                onShowSnackbar("Item deleted", null)
            }
        },
        onBackClicked = onBackClicked,
        coroutineScope = coroutineScope,
    )
}

@Composable
fun ItemScreen(
    modifier: Modifier = Modifier,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    isEditing: Boolean,
    item: Item,
    quantityInput: String,
    onNameChange: (String) -> Unit,
    nameHasError: Boolean = false,
    measureUnitHasError: Boolean = false,
    quantityHasError: Boolean = false,
    onMeasureUnitChange: (String) -> Unit,
    onExpiryDateChange: (Instant?) -> Unit,
    onQuantityChange: (String) -> Unit,
    onSaveClicked: () -> Boolean,
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    coroutineScope: CoroutineScope,
) {
    var category by rememberSaveable { mutableStateOf("") }
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    var showCategoryDialog by rememberSaveable { mutableStateOf(false) }
    var expiryDate = item.expiryDate?.let { formattedDate(item.expiryDate) } ?: ""
    val paddingMd = dimensionResource(R.dimen.p_md)
    val paddingSm = dimensionResource(R.dimen.p_sm)
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            ItemScreenTopBar(
                isEditing = isEditing,
                onBackClicked = onBackClicked,
                onDeleteClicked = onDeleteClicked,
                onSaveClicked = onSaveClicked,
                onShowSnackbar = onShowSnackbar,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = paddingSm,
                        end = paddingMd,
                        bottom = paddingSm
                    ),
                coroutineScope = coroutineScope,
            )
        },
        contentWindowInsets = WindowInsets(paddingMd, paddingMd, paddingMd, paddingMd)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_md))
        ) {
            OutlinedTextField(
                value = item.name,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.name)) },
                isError = nameHasError,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                ),
            )
            OutlinedTextField(
                value = categoriesString(item.categories),
                onValueChange = { category = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(expiryDate) {
                        awaitEachGesture {
                            // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                            // in the Initial pass to observe events before the text field consumes them
                            // in the Main pass.
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent =
                                waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null) {
                                // showCategoryDialog = true
                            }
                        }
                    },
                label = { Text(stringResource(R.string.category)) },
                trailingIcon = {
                    Icon(
                        imageVector = DispensAppIcons.ArrowDropDown,
                        contentDescription = null,
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                ),
            )
            OutlinedTextField(
                value = item.measureUnit,
                onValueChange = onMeasureUnitChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.measure_unit)) },
                isError = measureUnitHasError,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                ),
            )

            Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_sm))) {
                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .pointerInput(expiryDate) {
                            awaitEachGesture {
                                // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                                // in the Initial pass to observe events before the text field consumes them
                                // in the Main pass.
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent =
                                    waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    showDatePickerDialog = true
                                }
                            }
                        },
                    readOnly = true,
                    label = { Text(stringResource(R.string.expiry_date)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = DispensAppIcons.DateRange,
                            contentDescription = stringResource(R.string.expiry_date)
                        )
                    },
                )
                OutlinedTextField(
                    value = quantityInput,
                    onValueChange = onQuantityChange,
                    modifier = Modifier.weight(1f),
                    label = { Text(stringResource(R.string.qty)) },
                    isError = quantityHasError,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                    ),
                    singleLine = true
                )
            }
        }

        if (showDatePickerDialog) {
            DatePickerModal(
                initialDate = item.expiryDate,
                onDateSelected = onExpiryDateChange,
                onDismiss = { showDatePickerDialog = false }
            )
        }
    }
}

fun categoriesString(categories: List<Category>): String {
    return categories.joinToString { it.name }
}

@Composable
fun ItemScreenTopBar(
    isEditing: Boolean,
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Boolean,
    onDeleteClicked: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = DispensAppIcons.ArrowBack,
                contentDescription = null,
            )
        }
        if (isEditing) {
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = onDeleteClicked,
                colors = ButtonDefaults.outlinedButtonColors().copy(
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                )
            ) {
                Text(stringResource(R.string.delete))
            }
            Box(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.p_xs))) {}
        }
        OutlinedButton(
            onClick = {
                coroutineScope.launch {
                    if (onSaveClicked()) {
                        onBackClicked()
                        onShowSnackbar("Item saved", null)
                    }
                }
            },
            colors = ButtonDefaults.outlinedButtonColors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        ) {
            Text(stringResource(R.string.save))
        }
    }
}

@Composable
fun DatePickerModal(
    initialDate: Instant?,
    onDateSelected: (Instant?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate?.toEpochMilliseconds()
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val millis = datePickerState.selectedDateMillis
                onDateSelected(millis?.let { Instant.fromEpochMilliseconds(it) })
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(apiLevel = 35, showSystemUi = true, showBackground = true)
@Composable
fun ItemScreenPreview() {
    val item = Item(
        id = 0,
        name = "Some name",
        quantity = 1,
        measureUnit = "Kg",
        expiryDate = null,
        categories = emptyList(),
    )
    DispensAppTheme(dynamicColor = false) {
        ItemScreen(
            onShowSnackbar = { _, _ -> false },
            isEditing = true,
            item = item,
            quantityInput = "",
            onNameChange = {},
            onMeasureUnitChange = {},
            onExpiryDateChange = {},
            onQuantityChange = {},
            onSaveClicked = { false },
            onDeleteClicked = {},
            onBackClicked = {},
            coroutineScope = rememberCoroutineScope()
        )
    }
}

