package com.jeanmeza.dispensapp.ui.item.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
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
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable<ItemRoute>(
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
        ItemRoute(
            onBackClicked = onBackClicked,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
