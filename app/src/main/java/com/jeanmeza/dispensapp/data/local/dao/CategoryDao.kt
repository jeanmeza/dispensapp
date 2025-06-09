package com.jeanmeza.dispensapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jeanmeza.dispensapp.data.local.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getCategoriesStream(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategory(id: Int): CategoryEntity

    @Upsert
    suspend fun upsert(category: CategoryEntity)

    @Delete
    suspend fun delete(category: CategoryEntity)

}