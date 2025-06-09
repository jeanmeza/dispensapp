package com.jeanmeza.dispensapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeanmeza.dispensapp.data.local.dao.CategoryDao
import com.jeanmeza.dispensapp.data.local.dao.ItemDao
import com.jeanmeza.dispensapp.data.local.entities.CategoryEntity
import com.jeanmeza.dispensapp.data.local.entities.ItemEntity

@Database(
    entities = [ItemEntity::class, CategoryEntity::class],
    version = 2,
    exportSchema = false
)
abstract class DispensAppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    abstract fun categoryDao(): CategoryDao
}