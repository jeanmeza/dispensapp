package com.jeanmeza.dispensapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jeanmeza.dispensapp.data.model.Category

data class CategoryWithItems(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ItemCategoryCrossRef::class,
            parentColumn = "category_id",
            entityColumn = "item_id"
        )
    )
    val items: List<ItemEntity>,
)

fun CategoryWithItems.asModel() = Category(
    id = category.id,
    name = category.name,
    items = items.map(ItemEntity::asModel)
)

