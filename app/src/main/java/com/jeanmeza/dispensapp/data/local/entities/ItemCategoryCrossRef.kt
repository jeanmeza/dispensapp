package com.jeanmeza.dispensapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "items_categories",
    indices = [
        Index(value = ["item_id"]),
        Index(value = ["category_id"]),
    ],
    primaryKeys = ["item_id", "category_id"],
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class ItemCategoryCrossRef(
    @ColumnInfo(name = "item_id")
    val itemId: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int
)
