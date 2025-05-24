package com.jeanmeza.dispensapp.data.repository

import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<ItemEntity>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    suspend fun getItemStream(id: Int): ItemEntity

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: ItemEntity)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: ItemEntity)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(item: ItemEntity)
}