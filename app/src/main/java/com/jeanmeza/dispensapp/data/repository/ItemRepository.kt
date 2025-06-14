package com.jeanmeza.dispensapp.data.repository

import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import com.jeanmeza.dispensapp.data.local.entities.PopulatedItem
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<PopulatedItem>>

    /**
     * Retrieve all the expiring items from the the given data source.
     */
    fun getExpiringItemsStream(date: Long): Flow<List<PopulatedItem>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    suspend fun getItem(id: Int): PopulatedItem

    /**
     * Insert or update an item in the data source
     */
    suspend fun upsert(item: ItemEntity)

    /**
     * Delete item from the data source
     */
    suspend fun delete(item: ItemEntity)

}