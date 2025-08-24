package com.jeanmeza.dispensapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.ui.component.ItemCardList

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

