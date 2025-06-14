package com.jeanmeza.dispensapp.data.repository

import com.jeanmeza.dispensapp.data.local.dao.ItemDao
import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import com.jeanmeza.dispensapp.data.local.entities.PopulatedItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalItemRepository @Inject constructor(private val itemDao: ItemDao) : ItemRepository {
    override fun getAllItemsStream(): Flow<List<PopulatedItem>> = itemDao.getAllItemsStream()

    override fun getExpiringItemsStream(date: Long): Flow<List<PopulatedItem>> =
        itemDao.getExpiringItemsStream(date)

    override suspend fun getItem(id: Int): PopulatedItem = itemDao.getItem(id)

    override suspend fun upsert(item: ItemEntity) = itemDao.upsert(item)

    override suspend fun delete(item: ItemEntity) = itemDao.delete(item)

}