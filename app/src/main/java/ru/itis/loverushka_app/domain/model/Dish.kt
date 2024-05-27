package ru.itis.loverushka_app.domain.model


data class Dish(

    val dishId: Int,
    val name: String,
    val description: String,
    val photo: String,
    val authorId: String,
    val price: Int,
    val ingredients: String
)