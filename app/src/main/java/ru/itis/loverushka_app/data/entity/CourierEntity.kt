package ru.itis.loverushka_app.data.entity

data class CourierEntity (
    val courierId: Int,
    val phoneNumber: String,
    val deliveryMethod: DeliveryMethodEntity,
    val transportId: String
)