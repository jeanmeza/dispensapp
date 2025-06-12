package com.jeanmeza.dispensapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import com.jeanmeza.dispensapp.ui.categories.CategoryDialog
import com.jeanmeza.dispensapp.ui.component.DispensAppSearchBar
import com.jeanmeza.dispensapp.ui.item.navigation.navigateToItem
import com.jeanmeza.dispensapp.ui.navigation.DispensAppNavHost
import com.jeanmeza.dispensapp.ui.navigation.TopLevelDestination
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import kotlin.reflect.KClass


@Composable
fun DispensApp(
    appState: DispensAppState,
    modifier: Modifier = Modifier,
) {
    val currentDestination = appState.currentDestination
    var showCategoryDialog by rememberSaveable { mutableStateOf(false) }

    if (showCategoryDialog) {
        CategoryDialog(
            onDismiss = { showCategoryDialog = false },
            onShowSnackbar = appState::onShowSnackbar
        )
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            appState.topLevelDestinations.forEachIndexed { idx, destination ->
                val selected = currentDestination.isRouteInHierarchy(destination.route)
                val icon =
                    if (selected) destination.selectedIcon else destination.unselectedIcon
                item(
                    selected = selected,
                    onClick = {
                        appState.navigateToTopLevelDestination(destination)
                    },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(destination.iconTextId)) },
                )
            }
        },
    ) {
        val destination = appState.currentTopLevelDestination
        Scaffold(
            modifier = modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(
                    hostState = appState.snackBarHostState,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                )
            },
            floatingActionButton = {
                val function = when (destination) {
                    TopLevelDestination.HOME,
                    TopLevelDestination.EXPIRING -> {
                        { appState.navController.navigateToItem() }
                    }

                    TopLevelDestination.CATEGORIES -> {
                        { showCategoryDialog = true }
                    }

                    TopLevelDestination.SHOPPING_LIST -> TODO()
                    null -> null
                }
                if (function != null) {
                    FloatingActionButton(onClick = function) {
                        Icon(
                            imageVector = DispensAppIcons.Add,
                            contentDescription = null,
                        )
                    }
                }
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                    ),
            ) {
                var shouldShowTopAppBar = false
                if (destination != null) {
                    shouldShowTopAppBar = true
                    var searchQuery by rememberSaveable { mutableStateOf("") }
                    DispensAppSearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = {},
                    )
                }
                Box(
                    modifier = Modifier
                        .consumeWindowInsets(
                            if (shouldShowTopAppBar) {
                                WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                            } else {
                                WindowInsets(0, 0, 0, 0)
                            }
                        )
                ) {
                    DispensAppNavHost(appState = appState)
                }
            }
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false

