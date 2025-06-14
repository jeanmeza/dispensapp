package com.jeanmeza.dispensapp.data.model

import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import kotlinx.datetime.Instant

data class Item(
    val id: Int,
    val name: String,
    val quantity: Int,
    val measureUnit: String,
    val expiryDate: Instant?,
    val categories: List<Category>,
)

fun Item.asEntity() = ItemEntity(
    id = id,
    name = name,
    quantity = quantity,
    measureUnit = measureUnit,
    expiryDate = expiryDate,
)