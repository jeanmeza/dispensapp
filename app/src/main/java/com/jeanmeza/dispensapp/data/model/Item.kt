package com.jeanmeza.dispensapp.data.model

import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import kotlinx.datetime.Instant

data class Item(
    val id: Int,
    val category: Category? = null,
    val name: String,
    val quantity: Int,
    val measureUnit: String,
    val expiryDate: Instant? = null,
)

fun Item.asEntity() = ItemEntity(
    id = id,
    categoryId = category?.id,
    name = name,
    quantity = quantity,
    measureUnit = measureUnit,
    expiryDate = expiryDate
)