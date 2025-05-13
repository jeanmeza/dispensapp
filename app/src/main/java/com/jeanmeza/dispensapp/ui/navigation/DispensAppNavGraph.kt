package com.jeanmeza.dispensapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.ui.categories.CategoriesDestination
import com.jeanmeza.dispensapp.ui.categories.CategoriesScreen
import com.jeanmeza.dispensapp.ui.expiring.ExpiringDestination
import com.jeanmeza.dispensapp.ui.expiring.ExpiringScreen
import com.jeanmeza.dispensapp.ui.home.HomeDestination
import com.jeanmeza.dispensapp.ui.home.HomeScreen
import com.jeanmeza.dispensapp.ui.shoppinglist.ShoppingListDestination
import com.jeanmeza.dispensapp.ui.shoppinglist.ShoppingListScreen

@Composable
fun DispensAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(modifier = Modifier.padding(dimensionResource(R.dimen.p_md)))
        }
        composable(route = CategoriesDestination.route) {
            CategoriesScreen(modifier = Modifier.padding(dimensionResource(R.dimen.p_md)))
        }
        composable(route = ExpiringDestination.route) {
            ExpiringScreen(modifier = Modifier.padding(dimensionResource(R.dimen.p_md)))
        }
        composable(route = ShoppingListDestination.route) {
            ShoppingListScreen(modifier = Modifier.padding(dimensionResource(R.dimen.p_md)))
        }
    }
}