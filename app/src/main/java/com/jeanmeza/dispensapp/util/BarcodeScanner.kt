package com.jeanmeza.dispensapp.util

import android.content.Context
import android.util.Log
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "BarcodeScanner"

class BarcodeScanner @Inject constructor(
    @ApplicationContext appContext: Context
) {
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_EAN_13,
            Barcode.FORMAT_UPC_A
        )
        .build()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options)
    val barCodeResults = MutableStateFlow<String?>(null)

    suspend fun scan() {
        try {
            val result = scanner.startScan().await()
            barCodeResults.value = result.rawValue
            Log.d(TAG, barCodeResults.value.toString())
        } catch (e: Exception) {
            Log.e(TAG, "Failed to scan: $e")
        }
    }
}