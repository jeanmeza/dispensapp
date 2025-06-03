package com.jeanmeza.dispensapp.data.model

import com.jeanmeza.dispensapp.data.local.entities.ItemEntity
import java.time.LocalDate
import java.time.ZoneId

data class Item(
    val id: Int,
    val categoryId: Int?,
    val name: String,
    val quantity: Int,
    val measureUnit: String,
    val expiryDate: LocalDate?
)

fun Item.asEntity() = ItemEntity(
    id = id,
    categoryId = categoryId,
    name = name,
    quantity = quantity,
    measureUnit = measureUnit,
    expiryDate = expiryDate?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
)