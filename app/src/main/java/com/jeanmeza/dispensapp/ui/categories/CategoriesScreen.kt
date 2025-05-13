package com.jeanmeza.dispensapp.ui.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jeanmeza.dispensapp.R
import com.jeanmeza.dispensapp.ui.navigation.NavigationDestination

object CategoriesDestination : NavigationDestination {
    override val route: String = "category"

    override val titleRes: Int = R.string.categories
}

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.categories),
            style = MaterialTheme.typography.displayLarge,
        )
    }
}