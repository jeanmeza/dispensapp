package com.jeanmeza.dispensapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jeanmeza.dispensapp.data.model.Item
import java.time.Instant

@Entity(tableName = "item")
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
    val expiryDate: Long?
)

/**
 * Converts the local model to the external model for use by layers external to the data layer
 */
fun ItemEntity.asExternalModel() = Item(
    id = id,
    categoryId = categoryId,
    name = name,
    quantity = quantity,
    measureUnit = measureUnit,
    expiryDate = expiryDate?.let {
        Instant.ofEpochMilli(it)
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate()
    },
)

fun List<ItemEntity>.asExternalModel() = map(ItemEntity::asExternalModel)
