package ru.itis.loverushka_app.domain.model

data class Review(
    val reviewId: Int,
    val orderId: Int,
    val phoneNumber: String,
    val rating: Int,
    val date: String,
    val comment: String
)
