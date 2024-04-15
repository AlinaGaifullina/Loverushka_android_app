package ru.itis.loverushka_app.domain.model


data class Dish(

    val dishId: Int,
    val dishName: String,
    val dishDescription: String,
    val dishPhoto: String,
    val dishAuthor: String,
    val dishPrice: Int,
    val ingredients: String
)