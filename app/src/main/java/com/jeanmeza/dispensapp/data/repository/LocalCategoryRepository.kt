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

    override suspend fun insert(category: CategoryEntity) = categoryDao.insert(category)

    override suspend fun update(category: CategoryEntity) = categoryDao.update(category)

    override suspend fun delete(category: CategoryEntity) = categoryDao.delete(category)

    override suspend fun delete(categories: List<CategoryEntity>) = categoryDao.delete(categories)
}