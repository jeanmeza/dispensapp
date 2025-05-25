package com.jeanmeza.dispensapp.ui.shoppinglist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jeanmeza.dispensapp.R

@Composable
fun ShoppingListRoute(modifier: Modifier = Modifier) {
    ShoppingListScreen(modifier)
}

@Composable
fun ShoppingListScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.shopping_list),
            style = MaterialTheme.typography.displayLarge,
        )
    }
}
