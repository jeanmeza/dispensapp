package com.jeanmeza.dispensapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.ui.categories.CategoryDialog
import com.jeanmeza.dispensapp.ui.component.DispensAppSearchBar
import com.jeanmeza.dispensapp.ui.component.FabOptionsBottomSheet
import com.jeanmeza.dispensapp.ui.item.ItemDialog
import com.jeanmeza.dispensapp.ui.item.ItemScreenViewModel
import com.jeanmeza.dispensapp.ui.navigation.DispensAppNavHost
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import kotlin.reflect.KClass


@Composable
fun DispensApp(
    appState: DispensAppState,
    modifier: Modifier = Modifier,
) {
    val currentDestination = appState.currentDestination
    var showCategoryDialog by rememberSaveable { mutableStateOf(false) }
    var showModalBottomSheet by rememberSaveable { mutableStateOf(false) }
    var shouldShowTopAppBar by rememberSaveable { mutableStateOf(true) }
    var showItemDialog by rememberSaveable { mutableStateOf(false) }
    var itemIdToEdit by rememberSaveable { mutableStateOf<Int?>(null) }
    var scanItem by rememberSaveable { mutableStateOf(false) }

    if (showItemDialog) {
        ItemDialog(
            onDismiss = {
                itemIdToEdit = null
                showItemDialog = false
                scanItem = false
            },
            onShowSnackbar = appState::onShowSnackbar,
            viewModel = hiltViewModel<ItemScreenViewModel, ItemScreenViewModel.Factory>(
                key = "${itemIdToEdit}_${scanItem}_${System.currentTimeMillis()}"
            ) {
                it.create(itemIdToEdit, scanItem)
            }
        )
    }

    if (showModalBottomSheet) {
        FabOptionsBottomSheet(
            onDismiss = { showModalBottomSheet = false },
            onItemOptionSelected = {
                itemIdToEdit = null
                showItemDialog = true
            },
            onCategoryOptionSelected = { showCategoryDialog = true },
            onShoppingListOptionSelected = {},
        )
    }

    if (showCategoryDialog) {
        CategoryDialog(
            onDismiss = { showCategoryDialog = false },
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
                if (destination != null) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.p_sm)),
                        horizontalAlignment = Alignment.End,
                    ) {
                        FloatingActionButton(
                            onClick = {
                                showItemDialog = true
                                scanItem = true
                            },
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        ) {
                            Icon(
                                imageVector = DispensAppIcons.PhotoCamera,
                                contentDescription = null,
                            )
                        }
                        FloatingActionButton(
                            onClick = { showModalBottomSheet = true },
                            modifier =
                                modifier.sizeIn(
                                    minWidth = 76.dp,
                                    minHeight = 76.dp,
                                ),
                        ) {
                            Icon(
                                imageVector = DispensAppIcons.Add,
                                contentDescription = null,
                                modifier = Modifier.size(36.dp)
                            )
                        }
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
                if (destination != null && shouldShowTopAppBar) {
                    var searchQuery by rememberSaveable { mutableStateOf("") }
                    DispensAppSearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = {},
                    )
                }

                Box(
                    // Workaround for https://issuetracker.google.com/338478720
                    modifier = Modifier
                        .consumeWindowInsets(
                            if (destination != null && shouldShowTopAppBar) {
                                WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                            } else {
                                WindowInsets(0, 0, 0, 0)
                            }
                        )
                ) {
                    DispensAppNavHost(
                        appState = appState,
                        onItemClicked = {
                            itemIdToEdit = it
                            showItemDialog = true
                        },
                        onCategorySelectionStart = { shouldShowTopAppBar = false },
                        onCategorySelectionEnd = { shouldShowTopAppBar = true },
                    )
                }
            }
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false

