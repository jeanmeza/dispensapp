package com.jeanmeza.dispensapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jeanmeza.dispensapp.ui.component.DispensAppSearchBar

@Composable
fun DispensApp(modifier: Modifier = Modifier) {
    var query by rememberSaveable { mutableStateOf("") }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DispensAppSearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = {},
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding), contentAlignment = Alignment.Center) {
            Text("Hello, World!")
        }
    }
}
