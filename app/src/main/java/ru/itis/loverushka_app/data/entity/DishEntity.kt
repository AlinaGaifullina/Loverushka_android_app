package ru.itis.loverushka_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class DishEntity(

    @PrimaryKey
    val dishId: Int,

    val dishName: String,
    val dishDescription: String,
    val dishPhoto: String,
    val dishAuthor: String,
    val dishPrice: Int,
    val ingredients: String
)