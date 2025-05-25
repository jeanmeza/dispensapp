package com.jeanmeza.dispensapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jeanmeza.dispensapp.ui.DispensApp
import com.jeanmeza.dispensapp.ui.rememberDispensAppState
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberDispensAppState()
            DispensAppTheme {
                DispensApp(appState)
            }
        }
    }
}