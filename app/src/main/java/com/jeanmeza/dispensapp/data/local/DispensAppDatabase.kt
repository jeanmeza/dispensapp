package com.jeanmeza.dispensapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeanmeza.dispensapp.data.local.dao.ItemDao
import com.jeanmeza.dispensapp.data.local.entities.ItemEntity

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class DispensAppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}