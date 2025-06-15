package com.jeanmeza.dispensapp.ui.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.jeanmeza.dispensapp.data.model.Category

class CategorySelectionPreviewParameterProvider : PreviewParameterProvider<List<Category>> {
    override val values: Sequence<List<Category>>
        get() = sequenceOf(
            listOf(
                Category(1, "Category 1"),
                Category(2, "Category 2"),
                Category(3, "Category 3"),
            )
        )
}