package com.jeanmeza.dispensapp.ui.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeanmeza.dispensapp.util.BarcodeScanner

@Composable
fun ScanBarcodeDialog(
    onDismiss: () -> Unit,
    onScanSuccess: (String) -> Unit,
    barcodeScanner: BarcodeScanner,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
        )
    ) {
        val barcodeValue by barcodeScanner.barCodeResults.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            barcodeScanner.scan()
        }

        LaunchedEffect(barcodeValue) {
            if (!barcodeValue.isNullOrEmpty()) {
                onScanSuccess(barcodeValue!!)
            }
        }
    }
}
