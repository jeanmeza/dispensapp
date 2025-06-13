package com.jeanmeza.dispensapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons.Grocery
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme


@Composable
fun FabOptionsBottomSheet(
    onDismiss: () -> Unit,
    onItemOptionSelected: () -> Unit,
    onCategoryOptionSelected: () -> Unit,
    onShoppingListOptionSelected: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.p_xl),
                    top = dimensionResource(R.dimen.p_none),
                    end = dimensionResource(R.dimen.p_xl),
                    bottom = dimensionResource(R.dimen.p_md)
                )
        ) {
            ModalBottomSheetButton(
                onClick = {
                    onDismiss()
                    onItemOptionSelected()
                },
                label = "Item",
            ) {
                Grocery()
            }

            ModalBottomSheetButton(
                onClick = {
                    onDismiss()
                    onCategoryOptionSelected()
                },
                label = "Category",
            ) {
                Icon(
                    imageVector = DispensAppIcons.CategoriesOutlined,
                    contentDescription = stringResource(R.string.category),
                )
            }

            ModalBottomSheetButton(
                onClick = {
                    onDismiss()
                    onShoppingListOptionSelected()
                },
                label = "Shop",
            ) {
                Icon(
                    imageVector = DispensAppIcons.StorefrontOutlined,
                    contentDescription = stringResource(R.string.shop),
                )
            }

        }
    }
}

@Composable
fun ModalBottomSheetButton(
    onClick: () -> Unit,
    label: String,
    icon: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            onClick = onClick,
            content = icon,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FabOptionsBottomSheetPreview() {
    DispensAppTheme {
        FabOptionsBottomSheet(
            onDismiss = {},
            sheetState = rememberStandardBottomSheetState(),
            onItemOptionSelected = {},
            onCategoryOptionSelected = {},
            onShoppingListOptionSelected = {},
        )
    }
}