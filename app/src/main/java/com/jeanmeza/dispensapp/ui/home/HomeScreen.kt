package com.jeanmeza.dispensapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import com.jeanmeza.dispensapp.ui.theme.Shapes
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeRoute(
    onAddItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    HomeScreen(
        items = homeUiState.items,
        onAddItemClick = onAddItemClick,
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    items: List<Item>,
    onAddItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.p_md))
            .background(color = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.p_sm)),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = onAddItemClick,
                contentPadding = ButtonDefaults.TextButtonWithIconContentPadding,
                shape = Shapes.small,
                colors = ButtonDefaults.buttonColors()
                    .copy(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
            ) {
                Icon(
                    imageVector = DispensAppIcons.Add, contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Add item")
            }
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_md))) {
            items(items = items, key = { it.id }) {
                ItemCard(
                    item = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
            }
        }
    }
}

@Composable
private fun ItemCard(item: Item, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = Shapes.small,
        colors = CardDefaults.cardColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_sm)),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
                    .width(80.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_sm)),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = dimensionResource(R.dimen.p_sm))
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${item.quantity} ${item.measureUnit}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (item.expiryDate != null) {
                        Text(
                            text = "Expires ${item.expiryDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
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
    DispensAppTheme {
        HomeScreen(items, {})
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
    DispensAppTheme(darkTheme = true) {
        HomeScreen(items, {})
    }
}
