package ru.itis.loverushka_app.domain.model

data class Chef(
    val chefId: Int,
    val phoneNumber: String,
    val dishes: List<Int>,
    val certificate: String
)
