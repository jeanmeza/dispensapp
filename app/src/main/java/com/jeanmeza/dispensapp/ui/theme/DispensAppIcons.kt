package com.jeanmeza.dispensapp.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.jeanmeza.dispensapp.R

object DispensAppIcons {
    val Add = Icons.Rounded.Add
    val ArrowBack = Icons.AutoMirrored.Rounded.ArrowBack
    val Categories = Icons.Filled.Category
    val CategoriesOutlined = Icons.Outlined.Category
    val Edit = Icons.Rounded.Edit
    val Expiring = Icons.Filled.Timer
    val ExpiringOutlined = Icons.Outlined.Timer
    val Home = Icons.Filled.Home
    val HomeOutlined = Icons.Outlined.Home
    val Search = Icons.Rounded.Search
    val Settings = Icons.Rounded.Settings
    val StorefrontOutlined = Icons.Outlined.Storefront
    val ShoppingList = Icons.Filled.ShoppingCart
    val ShoppingListOutlined = Icons.Outlined.ShoppingCart

    @Composable
    fun Grocery() {
        Icon(
            painter = painterResource(R.drawable.rounded_grocery_24),
            contentDescription = "Grocery",
        )
    }

}
