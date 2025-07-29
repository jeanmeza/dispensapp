package com.jeanmeza.dispensapp.ui.expiring

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.ui.component.ItemCardList

@Composable
fun ExpiringRoute(
    onItemClick: (Int) -> Unit,
    viewModel: ExpiringViewModel = hiltViewModel()
) {
    val uiState by viewModel.expiringUiState.collectAsStateWithLifecycle()
    ItemCardList(
        items = uiState.expiringItems,
        onItemClick = onItemClick,
    )
}

