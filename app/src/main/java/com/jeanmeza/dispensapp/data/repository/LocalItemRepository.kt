package com.jeanmeza.dispensapp.data.repository

import com.jeanmeza.dispensapp.data.local.dao.ItemDao
import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalItemRepository @Inject constructor(private val itemDao: ItemDao) : ItemRepository {
    override fun getAllItemsStream(): Flow<List<ItemEntity>> = itemDao.getAllItems()

    override suspend fun getItem(id: Int): ItemEntity = itemDao.getItem(id)

    override suspend fun insert(item: ItemEntity) = itemDao.insert(item)

    override suspend fun delete(item: ItemEntity) = itemDao.delete(item)

    override suspend fun update(item: ItemEntity) = itemDao.update(item)
}