package com.jeanmeza.dispensapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.jeanmeza.dispensapp.ui.DispensAppState
import com.jeanmeza.dispensapp.ui.categories.navigation.categoriesScreen
import com.jeanmeza.dispensapp.ui.expiring.navigation.expiringScreen
import com.jeanmeza.dispensapp.ui.home.navigation.HomeRoute
import com.jeanmeza.dispensapp.ui.home.navigation.homeScreen
import com.jeanmeza.dispensapp.ui.shoppinglist.navigation.shoppingListScreen

@Composable
fun DispensAppNavHost(
    appState: DispensAppState,
    onAddItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        homeScreen(
            onAddItemClick = onAddItemClick
        )
        categoriesScreen()
        expiringScreen()
        shoppingListScreen()
    }
}