package com.jeanmeza.dispensapp.network

import com.jeanmeza.dispensapp.network.dto.NetworkItem
import retrofit2.http.GET
import retrofit2.http.Query

interface BarcodeApiService {

    /**
     * Returns the information about the item by its barcode
     */
    @GET("/api/v1/item")
    suspend fun getItem(@Query("barcode") barcode: String): List<NetworkItem>

}