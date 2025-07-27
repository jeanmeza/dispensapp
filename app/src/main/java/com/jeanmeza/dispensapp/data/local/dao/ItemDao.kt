package com.jeanmeza.dispensapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.jeanmeza.dispensapp.data.local.entities.ItemCategoryCrossRef
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
    fun getItemStream(id: Int): Flow<PopulatedItem?>

    @Transaction
    @Query("SELECT * FROM item WHERE id = :id")
    suspend fun getItem(id: Int): PopulatedItem

    @Upsert
    suspend fun upsert(item: ItemEntity): Long

    @Delete
    suspend fun delete(item: ItemEntity)

    @Query(
        """
        DELETE FROM items_categories 
        WHERE item_id = :itemId and category_id not in (:categoryIds)
    """
    )
    suspend fun deleteCategoriesCrossRef(itemId: Int, categoryIds: List<Int>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItemCategoryCrossRef(itemCategories: List<ItemCategoryCrossRef>)
}