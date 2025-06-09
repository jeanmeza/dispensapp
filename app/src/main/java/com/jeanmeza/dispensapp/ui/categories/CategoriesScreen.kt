package com.jeanmeza.dispensapp.ui.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Category

@Composable
fun CategoriesRoute(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.categoriesUiState.collectAsStateWithLifecycle()
    CategoriesScreen(
        modifier = modifier,
        categories = uiState.categories
    )
}

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    categories: List<Category>,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.p_md))
            .background(color = MaterialTheme.colorScheme.surface),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_md)),
        ) {
            items(items = categories, key = { it.id }) {
                Text(it.name)
            }
        }
    }
}