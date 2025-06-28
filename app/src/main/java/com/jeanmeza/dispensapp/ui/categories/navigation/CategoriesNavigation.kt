package com.jeanmeza.dispensapp.ui.categories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jeanmeza.dispensapp.ui.categories.CategoriesRoute
import kotlinx.serialization.Serializable

@Serializable
data object CategoriesRoute

fun NavController.navigateToCategories(navOptions: NavOptions) =
    navigate(CategoriesRoute, navOptions)

fun NavGraphBuilder.categoriesScreen(onSelectionStart: () -> Unit, onSelectionEnd: () -> Unit) {
    composable<CategoriesRoute> {
        CategoriesRoute(
            onSelectionStart = onSelectionStart,
            onSelectionEnd = onSelectionEnd
        )
    }
}
