package com.jeanmeza.dispensapp.ui.categoryitems

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.data.model.Category
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.component.ItemCardList
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme

@Composable
fun CategoryItemsDialog(
    onDismiss: () -> Unit,
    onItemClick: (Int) -> Unit,
    viewModel: CategoryItemsViewModel = hiltViewModel()
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
        ),
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        ItemCardList(
            items = uiState.category.items,
            onItemClick = onItemClick,
        )
    }
}

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
        ItemCardList(
            items = category.items,
            onItemClick = onItemClick,
            modifier = Modifier.padding(innerPadding),
        )
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
fun CategoryItemsScreenPreview() {
    DispensAppTheme {
        CategoryItemsScreen(
            category = Category(
                id = 1,
                name = "Category",
                items = listOf(
                    Item(
                        id = 1,
                        name = "Item",
                        quantity = 1,
                        measureUnit = "Kg",
                        expiryDate = null,
                        categories = emptyList(),
                        imageUri = null
                    )
                )
            ),
            modifier = Modifier.fillMaxSize(),
            onItemClick = {},
            onBackClicked = {}
        )
    }
}