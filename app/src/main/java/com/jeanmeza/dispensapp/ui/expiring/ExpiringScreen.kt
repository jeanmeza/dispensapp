package com.jeanmeza.dispensapp.ui.expiring

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
fun ExpiringRoute(modifier: Modifier = Modifier) {
    ExpiringScreen(modifier)
}

@Composable
fun ExpiringScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.expiring),
            style = MaterialTheme.typography.displayLarge,
        )
    }
}
