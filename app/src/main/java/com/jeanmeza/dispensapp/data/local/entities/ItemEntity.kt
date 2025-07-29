package com.jeanmeza.dispensapp.data.local.entities

import androidx.core.net.toUri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeanmeza.dispensapp.data.model.Item
import kotlin.time.Instant

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
    val expiryDate: Instant?,
    @ColumnInfo(name = "image_uri")
    val imageUri: String?,
)

fun ItemEntity.asModel() = Item(
    id = id,
    name = name,
    quantity = quantity,
    measureUnit = measureUnit,
    expiryDate = expiryDate,
    categories = emptyList(),
    imageUri = imageUri?.toUri()
)

fun List<ItemEntity>.asModel() = map(ItemEntity::asModel)