package ru.itis.loverushka_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class DishEntity(

    @PrimaryKey
    val dishId: Int,

    val name: String,
    val description: String,
    val photo: String,
    val author: String,
    val price: Int,
    val ingredients: String
)
