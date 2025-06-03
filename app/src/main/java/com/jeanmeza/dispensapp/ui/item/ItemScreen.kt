package com.jeanmeza.dispensapp.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime

@Composable
fun ItemRoute(
    viewModel: ItemScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.itemUiState.collectAsState()
    ItemScreen(
        item = uiState.item,
        onNameChange = viewModel::onNameChange,
        onMeasureUnitChange = viewModel::onMeasureUnitChange,
        onExpiryDateChange = viewModel::onExpiryDateChange,
        onQuantityChange = viewModel::onQuantityChange,
        onSaveClicked = viewModel::onSaveClicked,
    )
}

@OptIn(ExperimentalTime::class)
@Composable
fun ItemScreen(
    item: Item,
    onNameChange: (String) -> Unit,
    onMeasureUnitChange: (String) -> Unit,
    onExpiryDateChange: (Long?) -> Unit,
    onQuantityChange: (String) -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var category by rememberSaveable { mutableStateOf("") }
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var expiryDate = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                OutlinedButton(
                    onClick = onSaveClicked,
                    colors = ButtonDefaults.outlinedButtonColors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
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
                    value = item.quantity.toString(),
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
            DatePickerDialog(
                onDismissRequest = { showDatePickerDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        onExpiryDateChange(datePickerState.selectedDateMillis)
                        showDatePickerDialog = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePickerDialog = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                )
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    return formatter.format(Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()))
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
    DispensAppTheme {
        ItemScreen(
            item = item,
            onNameChange = {},
            onMeasureUnitChange = {},
            onExpiryDateChange = {},
            onQuantityChange = {},
            onSaveClicked = {},
        )
    }
}

