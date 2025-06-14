package com.jeanmeza.dispensapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(
    tableName = "item",
    indices = [
        Index(value = ["category_id"]),
    ],
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "category_id")
    val categoryId: Int?,
    val name: String,
    val quantity: Int,
    @ColumnInfo(name = "measure_unit")
    val measureUnit: String,
    @ColumnInfo(name = "expiry_date")
    val expiryDate: Instant?
)

