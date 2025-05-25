package com.jeanmeza.dispensapp.data.model

data class Item(
    val id: Int,
    val categoryId: Int?,
    val name: String,
    val quantity: Int,
    val expiryDate: Long?
)