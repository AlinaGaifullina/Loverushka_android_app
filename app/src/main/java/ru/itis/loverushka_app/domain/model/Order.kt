package ru.itis.loverushka_app.domain.model

data class Order (
    val orderId: Int,
    val phoneNumber: String,
    val address: String,
    val dishes: List<Int>,
    val numberOfDishes: List<Int>,
    val price: Int,
    val status: String,
    val date: String,
    val time: String,
    val payWay: String,
    val courierId: Int
)