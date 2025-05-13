package com.jeanmeza.dispensapp

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jeanmeza.dispensapp.ui.categories.CategoriesDestination
import com.jeanmeza.dispensapp.ui.component.DispensAppSearchBar
import com.jeanmeza.dispensapp.ui.expiring.ExpiringDestination
import com.jeanmeza.dispensapp.ui.home.HomeDestination
import com.jeanmeza.dispensapp.ui.navigation.DispensAppNavHost
import com.jeanmeza.dispensapp.ui.shoppinglist.ShoppingListDestination
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme

enum class DispensAppNavigationItem(@StringRes val titleResource: Int, val route: String) {
    HOME(R.string.home, HomeDestination.route),
    CATEGORIES(R.string.categories, CategoriesDestination.route),
    EXPIRING(R.string.expiring, ExpiringDestination.route),
    SHOPPING_LIST(R.string.shopping_list, ShoppingListDestination.route)
}

@Composable
fun DispensAppNavigationItem.icon(): ImageVector {
    return when (this) {
        DispensAppNavigationItem.HOME -> Icons.Filled.Home
        DispensAppNavigationItem.CATEGORIES -> ImageVector.vectorResource(R.drawable.outline_category_24)
        DispensAppNavigationItem.EXPIRING -> ImageVector.vectorResource(R.drawable.baseline_schedule_24)
        DispensAppNavigationItem.SHOPPING_LIST -> Icons.Outlined.ShoppingCart
    }
}


@Composable
fun DispensApp(navController: NavHostController = rememberNavController()) {
    var selectedScreen by rememberSaveable { mutableStateOf(HomeDestination.route) }
    var query by rememberSaveable { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DispensAppSearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = {},
            )
        }
    ) { innerPadding ->
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                DispensAppNavigationItem.entries.forEachIndexed { idx, entry ->
                    item(
                        selected = entry.route == selectedScreen,
                        onClick = {
                            selectedScreen = entry.route
                            navController.navigate(entry.route)
                        },
                        icon = {
                            Icon(
                                imageVector = entry.icon(),
                                contentDescription = stringResource(entry.titleResource)
                            )
                        },
                        label = { Text(text = stringResource(entry.titleResource)) },
                    )
                }
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            DispensAppNavHost(navController = navController)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 35)
@Composable
fun DispensAppPreview() {
    DispensAppTheme {
        DispensApp()
    }
}
