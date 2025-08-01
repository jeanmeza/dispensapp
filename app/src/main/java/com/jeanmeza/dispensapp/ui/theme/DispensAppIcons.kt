package com.jeanmeza.dispensapp.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SelectAll
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.jeanmeza.dispensapp.R

object DispensAppIcons {
    val Add = Icons.Rounded.Add
    val AddAPhoto = Icons.Rounded.AddAPhoto
    val ArrowBack = Icons.AutoMirrored.Rounded.ArrowBack
    val ArrowDropDown = Icons.Rounded.ArrowDropDown
    val ArrowDropUp = Icons.Rounded.ArrowDropUp
    val PhotoCamera = Icons.Rounded.PhotoCamera
    val Categories = Icons.Filled.Category
    val CategoriesOutlined = Icons.Outlined.Category
    val Checkbox = Icons.Rounded.CheckBox
    val CloseOutlined = Icons.Outlined.Close
    val DateRange = Icons.Rounded.DateRange
    val DeleteOutlined = Icons.Outlined.Delete
    val EditOutlined = Icons.Outlined.Edit
    val Expiring = Icons.Filled.Timer
    val ExpiringOutlined = Icons.Outlined.Timer
    val Home = Icons.Filled.Home
    val HomeOutlined = Icons.Outlined.Home
    val LabelOutlined = Icons.AutoMirrored.Outlined.Label
    val Search = Icons.Rounded.Search
    val SelectAll = Icons.Rounded.SelectAll
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
