package com.jeanmeza.dispensapp.data.model

import com.jeanmeza.dispensapp.data.local.entities.ItemEntity

data class Item(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val quantity: Int,
    val expiryDate: Long?
)

/**
 * Converts the local model to the external model for use by layers external to the data layer
 */
fun ItemEntity.toModel() = Item(
    id = id,
    categoryId = categoryId,
    name = name,
    quantity = quantity,
    expiryDate = expiryDate
)