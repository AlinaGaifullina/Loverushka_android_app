package ru.itis.loverushka_app.domain.model

data class Courier(
    val courierId: Int,
    val phoneNumber: String,
    val deliveryMethod: DeliveryMethod,
    val transportId: String
)
