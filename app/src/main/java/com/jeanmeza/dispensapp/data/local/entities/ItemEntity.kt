package com.jeanmeza.dispensapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(
    tableName = "item",
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val quantity: Int,
    @ColumnInfo(name = "measure_unit")
    val measureUnit: String,
    @ColumnInfo(name = "expiry_date")
    val expiryDate: Instant?
)

