package com.jeanmeza.dispensapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jeanmeza.dispensapp.data.local.entities.CategoryEntity
import com.jeanmeza.dispensapp.data.local.entities.CategoryWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getCategoriesStream(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id ORDER BY name ASC")
    fun getCategoryWithItemsStream(id: Int): Flow<CategoryWithItems>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategory(id: Int): CategoryEntity

    @Insert
    suspend fun insert(category: CategoryEntity)

    @Update
    suspend fun update(category: CategoryEntity)

    @Delete
    suspend fun delete(category: CategoryEntity)

    @Delete
    suspend fun delete(categories: List<CategoryEntity>)
}