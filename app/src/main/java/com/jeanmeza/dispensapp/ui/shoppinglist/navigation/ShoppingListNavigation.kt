package com.jeanmeza.dispensapp.ui.shoppinglist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jeanmeza.dispensapp.ui.shoppinglist.ShoppingListRoute
import kotlinx.serialization.Serializable

@Serializable
data object ShoppingListRoute

fun NavController.navigateToShoppingList(navOptions: NavOptions) =
    navigate(route = ShoppingListRoute, navOptions)

fun NavGraphBuilder.shoppingListScreen() {
    composable<ShoppingListRoute> {
        ShoppingListRoute()
    }
}

