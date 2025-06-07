package com.jeanmeza.dispensapp.ui.expiring.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jeanmeza.dispensapp.ui.expiring.ExpiringRoute
import kotlinx.serialization.Serializable

@Serializable
data object ExpiringRoute

fun NavController.navigateToExpiring(navOptions: NavOptions) =
    navigate(route = ExpiringRoute, navOptions)

fun NavGraphBuilder.expiringScreen(onItemClick: (Int) -> Unit) {
    composable<ExpiringRoute> {
        ExpiringRoute(onItemClick)
    }
}
