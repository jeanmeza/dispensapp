package com.jeanmeza.dispensapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.jeanmeza.dispensapp.data.model.Item

data class PopulatedItem(
    @Embedded
    val item: ItemEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id",
        entity = CategoryEntity::class,
    )
    val category: CategoryEntity?
)

fun PopulatedItem.asModel() = Item(
    id = item.id,
    category = category?.asModel(),
    name = item.name,
    quantity = item.quantity,
    measureUnit = item.measureUnit,
    expiryDate = item.expiryDate,
)

fun List<PopulatedItem>.asModel() = map(PopulatedItem::asModel)
