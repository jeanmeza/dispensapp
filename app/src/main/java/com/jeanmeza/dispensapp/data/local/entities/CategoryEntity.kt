package com.jeanmeza.dispensapp.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jeanmeza.dispensapp.data.model.Category

@Entity(
    tableName = "categories",
    indices = [
        Index(value = ["name"], unique = true)
    ]
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
)

fun CategoryEntity.asModel() = Category(
    id = id,
    name = name,
)

fun List<CategoryEntity>.asModel() = map(CategoryEntity::asModel)
