package com.jeanmeza.dispensapp.ui.categoryitems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.component.SmallItemCard
import com.jeanmeza.dispensapp.ui.previewprovider.ItemListProvider
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme

@Composable
fun CategoryItemsRoute(
    onBackClicked: () -> Unit,
    onItemClick: (Int) -> Unit,
    viewModel: CategoryItemsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CategoryItemsScreen(
        category = uiState.category,
        modifier = Modifier.fillMaxSize(),
        onItemClick = onItemClick,
        onBackClicked = onBackClicked,
    )
}

@Composable
fun CategoryItemsScreen(
    category: Category,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { CategoryItemsTopAppBar(category, onBackClicked) },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        ) {
            items(items = category.items, key = { it.id }) {
                SmallItemCard(
                    item = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onItemClick(it.id) })
                        .height(60.dp)
                        .padding(horizontal = dimensionResource(R.dimen.p_md))
                )
            }
        }
    }
}

@Composable
fun CategoryItemsTopAppBar(
    category: Category,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(category.name) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = DispensAppIcons.ArrowBack,
                    contentDescription = null,
                )
            }
        },
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CategoryItemsScreenPreview(
    @PreviewParameter(provider = ItemListProvider::class)
    items: List<Item>
) {
    DispensAppTheme {
        CategoryItemsScreen(
            category = Category(
                id = 1,
                name = "Category",
                items = items
            ),
            modifier = Modifier.fillMaxSize(),
            onItemClick = {},
            onBackClicked = {}
        )
    }
}