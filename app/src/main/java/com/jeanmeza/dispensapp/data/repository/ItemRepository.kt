package com.jeanmeza.dispensapp.data.repository

import com.jeanmeza.dispensapp.data.local.entities.ItemCategoryCrossRef
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
    suspend fun upsert(item: ItemEntity): Long

    /**
     * Delete item from the data source
     */
    suspend fun delete(item: ItemEntity)

    /**
     * Delete item-category association
     */
    suspend fun deleteCategoriesCrossRef(itemId: Int, categoryIds: List<Int>)

    /**
     * Insert the new item-category associations
     */
    suspend fun insertItemCategoryCrossRef(itemCategories: List<ItemCategoryCrossRef>)

}