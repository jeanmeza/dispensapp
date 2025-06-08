package com.jeanmeza.dispensapp.ui.item

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
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ItemRoute(
    onBackClicked: () -> Unit,
    afterDelete: () -> Unit,
    snackbarHostState: SnackbarHostState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: ItemScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.itemUiState.collectAsStateWithLifecycle()
    ItemScreen(
        snackbarHostState = snackbarHostState,
        onShowSnackbar = onShowSnackbar,
        isEditing = viewModel.isEditing,
        item = uiState.item,
        quantityInput = uiState.quantityInput,
        onNameChange = viewModel::onNameChange,
        onMeasureUnitChange = viewModel::onMeasureUnitChange,
        onExpiryDateChange = viewModel::onExpiryDateChange,
        onQuantityChange = viewModel::onQuantityChange,
        onSaveClicked = viewModel::onSaveClicked,
        onDeleteClicked = {
            viewModel.onDeleteClicked()
            afterDelete()
        },
        onBackClicked = onBackClicked,
    )
}

@Composable
fun ItemScreen(
    snackbarHostState: SnackbarHostState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    isEditing: Boolean,
    item: Item,
    quantityInput: String,
    onNameChange: (String) -> Unit,
    onMeasureUnitChange: (String) -> Unit,
    onExpiryDateChange: (Long?) -> Unit,
    onQuantityChange: (String) -> Unit,
    onSaveClicked: () -> Boolean,
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var category by rememberSaveable { mutableStateOf("") }
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    var expiryDate = formatDate(item.expiryDate)
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
                    )
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
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
            TextField(
                value = item.name,
                onValueChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.name)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                ),
            )
            TextField(
                value = category,
                onValueChange = { category = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.category)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                ),
            )
            TextField(
                value = item.measureUnit,
                onValueChange = onMeasureUnitChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.measure_unit)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                ),
            )

            Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_sm))) {
                TextField(
                    value = expiryDate,
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    readOnly = true,
                    label = { Text(stringResource(R.string.expiry_date)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = { showDatePickerDialog = true },
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.DateRange,
                                contentDescription = stringResource(R.string.expiry_date)
                            )
                        }
                    },
                )
                TextField(
                    value = quantityInput,
                    onValueChange = onQuantityChange,
                    modifier = Modifier.weight(1f),
                    label = { Text(stringResource(R.string.qty)) },
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

@Composable
fun ItemScreenTopBar(
    isEditing: Boolean,
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Boolean,
    onDeleteClicked: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
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
                CoroutineScope(Dispatchers.Main).launch {
                    if (onSaveClicked()) {
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
    initialDate: LocalDate?,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = localDateToMillis(initialDate)
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
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

fun formatDate(date: LocalDate?): String {
    return when (date) {
        null -> ""
        else -> DateTimeFormatter.ofPattern("dd/MM/uuuu").format(date)
    }
}

fun localDateToMillis(localDate: LocalDate?): Long? {
    return when (localDate) {
        null -> null
        else -> localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}

@Preview(apiLevel = 35, showSystemUi = true, showBackground = true)
@Composable
fun ItemScreenPreview() {
    val item = Item(
        id = 0,
        categoryId = 0,
        name = "Some name",
        quantity = 1,
        measureUnit = "Kg",
        expiryDate = null,
    )
    DispensAppTheme(dynamicColor = false) {
        ItemScreen(
            snackbarHostState = remember { SnackbarHostState() },
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
        )
    }
}

