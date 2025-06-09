package com.jeanmeza.dispensapp.data.model

import com.jeanmeza.dispensapp.data.local.entities.CategoryEntity

data class Category(
    val id: Int = 0,
    val name: String,
)

fun Category.asEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
    )
}