package com.jeanmeza.dispensapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeanmeza.dispensapp.data.model.Item

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

/**
 * Converts the local model to the external model for use by layers external to the data layer
 */
fun ItemEntity.toModel() = Item(
    id = id,
    categoryId = categoryId,
    name = name,
    quantity = quantity,
    expiryDate = expiryDate
)
