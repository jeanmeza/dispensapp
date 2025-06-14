package com.jeanmeza.dispensapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import com.jeanmeza.dispensapp.data.local.entities.PopulatedItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Transaction
    @Query(value = "SELECT * FROM item ORDER BY name ASC")
    fun getAllItemsStream(): Flow<List<PopulatedItem>>

    @Transaction
    @Query("SELECT * FROM item WHERE expiry_date < :date ORDER BY expiry_date ASC")
    fun getExpiringItemsStream(date: Long): Flow<List<PopulatedItem>>

    @Transaction
    @Query("SELECT * FROM item WHERE id = :id")
    suspend fun getItem(id: Int): PopulatedItem

    @Upsert
    suspend fun upsert(item: ItemEntity)

    @Delete
    suspend fun delete(item: ItemEntity)

}