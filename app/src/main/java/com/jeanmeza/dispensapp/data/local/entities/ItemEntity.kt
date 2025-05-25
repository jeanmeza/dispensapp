package com.jeanmeza.dispensapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int?,
    val name: String,
    val quantity: Int,
    @ColumnInfo(name = "expiry_date")
    val expiryDate: Long?
)