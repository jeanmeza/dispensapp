package com.jeanmeza.dispensapp.ui.categoryitems.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jeanmeza.dispensapp.ui.categoryitems.CategoryItemsRoute
import kotlinx.serialization.Serializable

@Serializable
data class CategoryItemsRoute(
    val categoryId: Int,
)

fun NavController.navigateToCategoryItems(
    categoryId: Int,
    navOptions: NavOptions? = null,
) = navigate(route = CategoryItemsRoute(categoryId), navOptions)

fun NavGraphBuilder.categoryItemsScreen(
    onBackClicked: () -> Unit,
    onItemClicked: (Int) -> Unit,
) {
    composable<CategoryItemsRoute>(
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(300)
            )
        }
    ) {
        CategoryItemsRoute(
            onBackClicked = onBackClicked,
            onItemClick = onItemClicked,
        )
    }
}
