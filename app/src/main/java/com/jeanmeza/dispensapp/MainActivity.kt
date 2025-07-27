package com.jeanmeza.dispensapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jeanmeza.dispensapp.ui.DispensApp
import com.jeanmeza.dispensapp.ui.rememberDispensAppState
import com.jeanmeza.dispensapp.ui.theme.DispensAppTheme
import com.jeanmeza.dispensapp.util.BarcodeScanner
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var barcodeScanner: BarcodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberDispensAppState(barcodeScanner = barcodeScanner)
            DispensAppTheme {
                DispensApp(appState)
            }
        }
    }
}