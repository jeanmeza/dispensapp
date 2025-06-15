package com.jeanmeza.dispensapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jeanmeza.dispensapp.data.local.dao.CategoryDao
import com.jeanmeza.dispensapp.data.local.dao.ItemDao
import com.jeanmeza.dispensapp.data.local.entities.CategoryEntity
import com.jeanmeza.dispensapp.data.local.entities.ItemCategoryCrossRef
import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import com.jeanmeza.dispensapp.data.local.util.InstantConverter

@Database(
    entities = [
        ItemEntity::class,
        CategoryEntity::class,
        ItemCategoryCrossRef::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    InstantConverter::class,
)
abstract class DispensAppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    abstract fun categoryDao(): CategoryDao
}