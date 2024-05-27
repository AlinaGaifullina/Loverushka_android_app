package ru.itis.loverushka_app.domain.model

data class Address(
    val addressId: Int,
    val phoneNumber: String,
    val city: String,
    val streetName: String,
    val streetNumber: String,
    val flat: Int,
    val floor: Int
)
