package com.jeanmeza.dispensapp.ui.item.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jeanmeza.dispensapp.ui.item.ItemRoute
import kotlinx.serialization.Serializable

@Serializable
data class ItemRoute(
    val itemId: Int? = null,
)

fun NavController.navigateToItem(
    itemId: Int? = null,
    navOptions: NavOptions? = null,
) = navigate(route = ItemRoute(itemId), navOptions)

fun NavGraphBuilder.itemScreen(
    onBackClicked: () -> Unit,
    afterDelete: () -> Unit,
    snackbarHostState: SnackbarHostState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<ItemRoute> {
        ItemRoute(
            onBackClicked = onBackClicked,
            afterDelete = afterDelete,
            snackbarHostState = snackbarHostState,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
