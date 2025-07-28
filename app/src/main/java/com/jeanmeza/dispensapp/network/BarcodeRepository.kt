package com.jeanmeza.dispensapp.network

import com.jeanmeza.dispensapp.network.dto.NetworkItem
import javax.inject.Inject

interface BarcodeRepository {
    /**
     * Fetches the item from the barcode API
     */
    suspend fun getItem(barcode: String): List<NetworkItem>
}

class NetworkBarcodeRepository @Inject constructor(
    private val barcodeApiService: BarcodeApiService
) : BarcodeRepository {
    override suspend fun getItem(barcode: String): List<NetworkItem> =
        barcodeApiService.getItem(barcode)

}