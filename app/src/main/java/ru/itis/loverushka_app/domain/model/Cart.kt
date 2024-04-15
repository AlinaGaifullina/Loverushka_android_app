package ru.itis.loverushka_app.domain.model

data class Cart(
    val cartId: Int,
    val userPhoneNumber: String,
    val address: String,
    val dishes: List<Int>,
    val price: Int,
    val checkedDishes: List<Int>,
    val numberOfDishes: List<Int>,
)
