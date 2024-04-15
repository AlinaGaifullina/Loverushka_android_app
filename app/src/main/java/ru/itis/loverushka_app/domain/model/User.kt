package ru.itis.loverushka_app.domain.model


data class User(

    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val password: String,
    val country: String,
    val city: String,
    val cartId: Int,
    val ordersId: List<Int>
)