package com.jeanmeza.dispensapp.data.local.entities

import androidx.core.net.toUri
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jeanmeza.dispensapp.data.model.Item

data class PopulatedItem(
    @Embedded
    val item: ItemEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ItemCategoryCrossRef::class,
            parentColumn = "item_id",
            entityColumn = "category_id"
        )
    )
    val categories: List<CategoryEntity>,
)

fun PopulatedItem.asModel() = Item(
    id = item.id,
    name = item.name,
    quantity = item.quantity,
    measureUnit = item.measureUnit,
    expiryDate = item.expiryDate,
    categories = categories.map(CategoryEntity::asModel),
    imageUri = item.imageUri?.toUri(),
)

fun List<PopulatedItem>.asModel() = map(PopulatedItem::asModel)

fun Item.categoriesCrossRef() = categories.map {
    ItemCategoryCrossRef(
        itemId = this.id,
        categoryId = it.id,
    )
}