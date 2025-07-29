package com.jeanmeza.dispensapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.data.model.Item
import com.jeanmeza.dispensapp.ui.component.ItemCardList
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlin.time.Clock

@Composable
fun HomeRoute(
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    ItemCardList(
        items = homeUiState.items,
        onItemClick = onItemClick,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val items = listOf(
        Item(
            id = 1,
            name = "Pasta",
            quantity = 10,
            measureUnit = "Kg",
            expiryDate = Clock.System.now(),
            categories = emptyList(),
            imageUri = null,
        ),
        Item(
            id = 2,
            name = "Latte",
            quantity = 10,
            measureUnit = "Kg",
            expiryDate = Clock.System.now(),
            categories = emptyList(),
            imageUri = null,
        ),
        Item(
            id = 3,
            name = "Latte",
            quantity = 1,
            measureUnit = "Kg",
            expiryDate = Clock.System.now()
                .plus(4L, DateTimeUnit.DAY, TimeZone.currentSystemDefault()),
            categories = emptyList(),
            imageUri = null,
        ),
    )
    DispensAppTheme(darkTheme = false, dynamicColor = false) {
        ItemCardList(
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
            name = "Pasta",
            quantity = 10,
            measureUnit = "Kg",
            expiryDate = Clock.System.now(),
            categories = emptyList(),
            imageUri = null,
        ),
        Item(
            id = 2,
            name = "Latte",
            quantity = 10,
            measureUnit = "Kg",
            expiryDate = Clock.System.now(),
            categories = emptyList(),
            imageUri = null,
        ),
        Item(
            id = 3,
            name = "Latte",
            quantity = 1,
            measureUnit = "Kg",
            expiryDate = Clock.System.now()
                .plus(4L, DateTimeUnit.DAY, TimeZone.currentSystemDefault()),
            categories = emptyList(),
            imageUri = null,
        ),
    )
    DispensAppTheme(darkTheme = true, dynamicColor = false) {
        ItemCardList(
            items = items,
            onItemClick = {},
        )
    }
}
