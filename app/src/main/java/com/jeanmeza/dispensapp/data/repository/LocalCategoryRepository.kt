package com.jeanmeza.dispensapp.data.repository

import com.jeanmeza.dispensapp.data.local.dao.CategoryDao
import com.jeanmeza.dispensapp.data.local.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalCategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun getCategoriesStream(): Flow<List<CategoryEntity>> =
        categoryDao.getCategoriesStream()

    override suspend fun getCategory(id: Int): CategoryEntity = categoryDao.getCategory(id)

    override suspend fun upsert(category: CategoryEntity) = categoryDao.upsert(category)

    override suspend fun delete(category: CategoryEntity) = categoryDao.delete(category)
}