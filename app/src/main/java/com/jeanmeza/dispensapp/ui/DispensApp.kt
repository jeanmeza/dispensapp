package com.jeanmeza.dispensapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
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
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.ui.categories.CategoryDialog
import com.jeanmeza.dispensapp.ui.categoryitems.navigation.navigateToCategoryItems
import com.jeanmeza.dispensapp.ui.component.FabOptionsBottomSheet
import com.jeanmeza.dispensapp.ui.item.navigation.navigateToItem
import com.jeanmeza.dispensapp.ui.navigation.DispensAppNavHost
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import kotlin.reflect.KClass


@Composable
fun DispensApp(
    appState: DispensAppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val currentDestination = appState.currentDestination
    var showCategoryDialog by rememberSaveable { mutableStateOf(false) }
    var showModalBottomSheet by rememberSaveable { mutableStateOf(false) }
    var shouldShowTopAppBar by rememberSaveable { mutableStateOf(true) }

    if (showModalBottomSheet) {
        FabOptionsBottomSheet(
            onDismiss = { showModalBottomSheet = false },
            onItemOptionSelected = {
                appState.navController.navigateToItem()
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

    val destination = appState.currentTopLevelDestination
    val isTopLevelDestination = destination != null
    shouldShowTopAppBar = isTopLevelDestination

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
        layoutType = if (!isTopLevelDestination) NavigationSuiteType.None
        else NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    ) {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding(),
            topBar = {
                AnimatedVisibility(
                    visible = isTopLevelDestination && shouldShowTopAppBar,
                ) {
                    TopAppBar(
                        title = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(R.string.app_name),
                                    style = MaterialTheme.typography.headlineMedium,
                                )
                            }
                        }
                    )
                }
            },
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
                                appState.navController.navigateToItem(startScan = true)
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
            ) {
                DispensAppNavHost(
                    appState = appState,
                    onItemClicked = { itemId ->
                        appState.navController.navigateToItem(itemId)
                    },
                    onCategoryClicked = {
                        appState.navController.navigateToCategoryItems(it)
                    },
                    onCategorySelectionStart = { shouldShowTopAppBar = false },
                    onCategorySelectionEnd = { shouldShowTopAppBar = true },
                )
            }
        }
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false