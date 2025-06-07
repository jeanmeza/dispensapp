package com.jeanmeza.dispensapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM item ORDER BY name ASC")
    fun getAllItemsStream(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM item WHERE expiry_date < :date ORDER BY expiry_date ASC")
    fun getExpiringItemsStream(date: Long): Flow<List<ItemEntity>>

    @Query("SELECT * FROM item WHERE id = :id")
    suspend fun getItem(id: Int): ItemEntity

    @Upsert
    suspend fun upsert(item: ItemEntity)

    @Delete
    suspend fun delete(item: ItemEntity)

}