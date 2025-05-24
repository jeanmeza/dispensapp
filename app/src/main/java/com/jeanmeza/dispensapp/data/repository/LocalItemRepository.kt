package com.jeanmeza.dispensapp.data.repository

import com.jeanmeza.dispensapp.data.local.dao.ItemDao
import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalItemRepository @Inject constructor(private val itemDao: ItemDao) : ItemRepository {
    override fun getAllItemsStream(): Flow<List<ItemEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getItemStream(id: Int): ItemEntity {
        TODO("Not yet implemented")
    }

    override suspend fun insertItem(item: ItemEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: ItemEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateItem(item: ItemEntity) {
        TODO("Not yet implemented")
    }
}