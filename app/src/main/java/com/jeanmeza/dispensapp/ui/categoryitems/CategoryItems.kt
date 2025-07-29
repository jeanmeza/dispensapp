package com.jeanmeza.dispensapp.ui.categoryitems

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.ui.component.ItemCardList

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
