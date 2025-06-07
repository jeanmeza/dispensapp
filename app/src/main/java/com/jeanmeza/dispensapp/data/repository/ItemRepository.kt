package com.jeanmeza.dispensapp.data.repository

import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<ItemEntity>>

    /**
     * Retrieve all the expiring items from the the given data source.
     */
    fun getExpiringItemsStream(date: Long): Flow<List<ItemEntity>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    suspend fun getItem(id: Int): ItemEntity

    /**
     * Insert or update an item in the data source
     */
    suspend fun upsert(item: ItemEntity)

    /**
     * Delete item from the data source
     */
    suspend fun delete(item: ItemEntity)

}