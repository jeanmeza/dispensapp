package com.jeanmeza.dispensapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.jeanmeza.dispensapp.ui.component.DispensAppSearchBar
import com.jeanmeza.dispensapp.ui.navigation.DispensAppNavHost
import kotlin.reflect.KClass


@Composable
fun DispensApp(
    appState: DispensAppState,
    modifier: Modifier = Modifier,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DispensAppSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {},
            )
        }
    ) { innerPadding ->
        val currentDestination = appState.currentDestination
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                appState.topLevelDestinations.forEachIndexed { idx, destination ->
                    val selected = currentDestination.isRouteInHierarchy(destination.route)
                    item(
                        selected = selected,
                        onClick = {
                            appState.navigateToTopLevelDestination(destination)
                        },
                        icon = {
                            Icon(
                                imageVector =
                                    if (selected) destination.selectedIcon
                                    else destination.unselectedIcon,
                                contentDescription = null
                            )
                        },
                        label = { Text(text = stringResource(destination.iconTextId)) },
                    )
                }
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            DispensAppNavHost(appState)
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false

