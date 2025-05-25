package com.jeanmeza.dispensapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.ui.categories.navigation.CategoriesRoute
import com.jeanmeza.dispensapp.ui.expiring.navigation.ExpiringRoute
import com.jeanmeza.dispensapp.ui.home.navigation.HomeRoute
import com.jeanmeza.dispensapp.ui.shoppinglist.navigation.ShoppingListRoute
import com.jeanmeza.dispensapp.ui.theme.DispensAppIcons
import kotlin.reflect.KClass


enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
) {
    HOME(
        selectedIcon = DispensAppIcons.Home,
        unselectedIcon = DispensAppIcons.HomeOutlined,
        iconTextId = R.string.home,
        titleTextId = R.string.home,
        route = HomeRoute::class,
    ),
    CATEGORIES(
        selectedIcon = DispensAppIcons.Categories,
        unselectedIcon = DispensAppIcons.CategoriesOutlined,
        iconTextId = R.string.categories,
        titleTextId = R.string.categories,
        route = CategoriesRoute::class,
    ),
    EXPIRING(
        selectedIcon = DispensAppIcons.Expiring,
        unselectedIcon = DispensAppIcons.ExpiringOutlined,
        iconTextId = R.string.expiring,
        titleTextId = R.string.expiring,
        route = ExpiringRoute::class,
    ),
    SHOPPING_LIST(
        selectedIcon = DispensAppIcons.ShoppingList,
        unselectedIcon = DispensAppIcons.ShoppingListOutlined,
        iconTextId = R.string.shopping_list,
        titleTextId = R.string.shopping_list,
        route = ShoppingListRoute::class,
    )
}
