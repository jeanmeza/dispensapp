package com.jeanmeza.dispensapp.ui.item.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jeanmeza.dispensapp.ui.item.ItemRoute
import kotlinx.serialization.Serializable

const val ITEM_ID_KEY = "itemId"

@Serializable
data class ItemRoute(
    val itemId: Int? = null,
)

fun NavController.navigateToItem(
    itemId: Int? = null,
    navOptions: NavOptions? = null,
) = navigate(route = ItemRoute(itemId), navOptions)

fun NavGraphBuilder.itemScreen() {
    composable<ItemRoute>() {
        ItemRoute()
    }
}
