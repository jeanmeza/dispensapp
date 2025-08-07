package com.jeanmeza.dispensapp.ui

//import com.jeanmeza.dispensapp.ui.navigation.TopLevelDestination.SHOPPING_LIST
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.jeanmeza.dispensapp.ui.categories.navigation.navigateToCategories
import com.jeanmeza.dispensapp.ui.expiring.navigation.navigateToExpiring
import com.jeanmeza.dispensapp.ui.home.navigation.navigateToHome
import com.jeanmeza.dispensapp.ui.navigation.TopLevelDestination
import com.jeanmeza.dispensapp.ui.navigation.TopLevelDestination.CATEGORIES
import com.jeanmeza.dispensapp.ui.navigation.TopLevelDestination.EXPIRING
import com.jeanmeza.dispensapp.ui.navigation.TopLevelDestination.HOME

@Composable
fun rememberDispensAppState(
    navController: NavHostController = rememberNavController(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
): DispensAppState {
    return remember(navController) {
        DispensAppState(navController, snackBarHostState)
    }
}

@Stable
class DispensAppState(
    val navController: NavHostController,
    val snackBarHostState: SnackbarHostState
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            }
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            when (topLevelDestination) {
                HOME -> navController.navigateToHome(topLevelNavOptions)
                CATEGORIES -> navController.navigateToCategories(topLevelNavOptions)
                EXPIRING -> navController.navigateToExpiring(topLevelNavOptions)
//                SHOPPING_LIST -> navController.navigateToShoppingList(topLevelNavOptions)
            }
        }
    }

    suspend fun onShowSnackbar(message: String, action: String?): Boolean {
        return snackBarHostState.showSnackbar(
            message = message,
            actionLabel = action,
            duration = SnackbarDuration.Short
        ) == SnackbarResult.ActionPerformed
    }
}
