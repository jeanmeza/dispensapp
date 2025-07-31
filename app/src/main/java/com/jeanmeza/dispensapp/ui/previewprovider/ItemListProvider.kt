package com.jeanmeza.dispensapp.ui.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jeanmeza.dispensapp.data.model.Item

/**
 * A [PreviewParameterProvider] for [com.jeanmeza.dispensapp.ui.categoryitems.CategoryItemsScreen].
 * The items in the list do not contain information about the categories.
 */
class ItemListProvider : PreviewParameterProvider<List<Item>> {
    override val values: Sequence<List<Item>>
        get() = sequenceOf(
            listOf(
                Item(
                    id = 1,
                    name = "Item 1",
                    quantity = 1,
                    measureUnit = "Kg",
                    expiryDate = null,
                    categories = emptyList(),
                    imageUri = null,
                ),
                Item(
                    id = 2,
                    name = "Item 2",
                    quantity = 1,
                    measureUnit = "Kg",
                    expiryDate = null,
                    categories = emptyList(),
                    imageUri = null,
                ),
                Item(
                    id = 3,
                    name = "Item 3",
                    quantity = 1,
                    measureUnit = "Kg",
                    expiryDate = null,
                    categories = emptyList(),
                    imageUri = null,
                )
            )
        )
}