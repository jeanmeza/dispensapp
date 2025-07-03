package com.jeanmeza.dispensapp.data.model

import android.net.Uri
import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import kotlin.time.Instant

data class Item(
    val id: Int,
    val name: String,
    val quantity: Int,
    val measureUnit: String,
    val expiryDate: Instant?,
    val categories: List<Category>,
    val imageUri: Uri?,
)

fun Item.asEntity() = ItemEntity(
    id = id,
    name = name,
    quantity = quantity,
    measureUnit = measureUnit,
    expiryDate = expiryDate,
    imageUri = imageUri?.toString()
)