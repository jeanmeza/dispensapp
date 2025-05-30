package com.jeanmeza.dispensapp.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.ExperimentalTime


@Composable
fun ItemDialog(
    onDismiss: () -> Unit,
    viewModel: ItemDialogViewModel = hiltViewModel()
) {
    ItemDialog(onDismiss = onDismiss)
}

@OptIn(ExperimentalTime::class)
@Composable
fun ItemDialog(onDismiss: () -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var measureUnit by rememberSaveable { mutableStateOf("") }
    var quantity by rememberSaveable { mutableStateOf("1") }
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var expiryDate = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {}) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        title = {
            Text(
                text = stringResource(R.string.new_item),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = dimensionResource(R.dimen.p_sm)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_md))
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                    ),
                )
                TextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text(stringResource(R.string.category)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                    ),
                )
                TextField(
                    value = measureUnit,
                    onValueChange = { measureUnit = it },
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
                        modifier = Modifier.weight(2f),
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
                        value = quantity,
                        onValueChange = { quantity = it },
                        modifier = Modifier.weight(1f),
                        label = { Text(stringResource(R.string.qty)) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                        ),
                        singleLine = true
                    )
                }
                HorizontalDivider(Modifier.padding(top = dimensionResource(R.dimen.p_sm)))
            }

            if (showDatePickerDialog) {
                DatePickerDialog(
                    onDismissRequest = { showDatePickerDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
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
                    DatePicker(state = datePickerState)
                }
            }
        },
    )
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun ItemDialogPreview() {
    DispensAppTheme {
        ItemDialog(onDismiss = {})
    }
}

