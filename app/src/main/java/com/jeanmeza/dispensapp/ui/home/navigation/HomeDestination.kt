package com.jeanmeza.dispensapp.ui.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jeanmeza.dispensapp.ui.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = HomeRoute, navOptions)

fun NavGraphBuilder.homeScreen(onItemClick: (Int) -> Unit) {
    composable<HomeRoute> {
        HomeRoute(onItemClick = onItemClick)
    }
}