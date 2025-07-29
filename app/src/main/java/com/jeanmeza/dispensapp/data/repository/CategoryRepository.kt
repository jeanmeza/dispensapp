package com.jeanmeza.dispensapp.data.repository

import com.jeanmeza.dispensapp.data.local.entities.CategoryEntity
import com.jeanmeza.dispensapp.data.local.entities.CategoryWithItems
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    /**
     * Retrieve all the categories from the the given data source.
     */
    fun getCategoriesStream(): Flow<List<CategoryEntity>>

    fun getCategoriesWithItemsStream(categoryId: Int): Flow<CategoryWithItems>

    /**
     * Retrieve an category from the given data source that matches with the [id].
     */
    suspend fun getCategory(id: Int): CategoryEntity

    /**
     * Insert a new category in the data source
     */
    suspend fun insert(category: CategoryEntity)

    /**
     * Update an category in the data source
     */
    suspend fun update(category: CategoryEntity)

    /**
     * Delete category from the data source
     */
    suspend fun delete(category: CategoryEntity)

    /**
     * Delete all categories from the data source
     */
    suspend fun delete(categories: List<CategoryEntity>)

}