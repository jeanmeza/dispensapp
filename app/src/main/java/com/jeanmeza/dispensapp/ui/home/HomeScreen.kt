package com.jeanmeza.dispensapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.component.ItemCard
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import java.time.LocalDate

@Composable
fun HomeRoute(
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    HomeScreen(
        items = homeUiState.items,
        onItemClick = onItemClick,
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    items: List<Item>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
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
            items(items = items, key = { it.id }) {
                ItemCard(
                    item = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onItemClick(it.id) })
                        .height(80.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val items = listOf(
        Item(
            id = 1,
            categoryId = null,
            name = "Pasta",
            quantity = 10,
            measureUnit = "Kg",
            expiryDate = LocalDate.now()
        ),
        Item(
            id = 2,
            categoryId = null,
            name = "Tonno",
            quantity = 10,
            measureUnit = "Kg",
            expiryDate = LocalDate.now()
        ),
        Item(
            id = 3,
            categoryId = null,
            name = "Riso",
            quantity = 10,
            measureUnit = "Kg",
            expiryDate = null,
        ),
    )
    DispensAppTheme(darkTheme = false, dynamicColor = false) {
        HomeScreen(
            items = items,
            onItemClick = {},
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreviewDark() {
    val items = listOf(
        Item(
            id = 1,
            categoryId = null,
            name = "Pasta",
            quantity = 10,
            measureUnit = "Kg",
            expiryDate = LocalDate.now()
        ),
        Item(
            id = 2,
            categoryId = null,
            name = "Latte",
            quantity = 10,
            measureUnit = "Kg",
            expiryDate = LocalDate.now()
        ),
        Item(
            id = 3,
            categoryId = null,
            name = "Latte",
            quantity = 1,
            measureUnit = "Kg",
            expiryDate = LocalDate.now().plusDays(4),
        ),
    )
    DispensAppTheme(darkTheme = true, dynamicColor = false) {
        HomeScreen(
            items = items,
            onItemClick = {},
        )
    }
}
