package com.jeanmeza.dispensapp.data.model

import java.time.LocalDate

data class Item(
    val id: Int,
    val categoryId: Int?,
    val name: String,
    val quantity: Int,
    val measureUnit: String,
    val expiryDate: LocalDate?
)