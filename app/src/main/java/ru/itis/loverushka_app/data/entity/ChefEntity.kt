package ru.itis.loverushka_app.data.entity

data class ChefEntity(
    val chefId: Int,
    val phoneNumber: String,
    val dishes: List<Int>,
    val certificate: String
)
