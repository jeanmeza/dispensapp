package com.jeanmeza.dispensapp.data.repository

import com.jeanmeza.dispensapp.data.local.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    /**
     * Retrieve all the categories from the the given data source.
     */
    fun getCategoriesStream(): Flow<List<CategoryEntity>>

    /**
     * Retrieve an category from the given data source that matches with the [id].
     */
    suspend fun getCategory(id: Int): CategoryEntity

    /**
     * Insert or update an category in the data source
     */
    suspend fun upsert(category: CategoryEntity)

    /**
     * Delete category from the data source
     */
    suspend fun delete(category: CategoryEntity)
}