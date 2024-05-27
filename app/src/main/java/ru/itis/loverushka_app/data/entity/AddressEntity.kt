package ru.itis.loverushka_app.data.entity

import androidx.room.PrimaryKey

data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val addressId: Int,
    val phoneNumber: String,
    val city: String,
    val streetName: String,
    val streetNumber: String,
    val flat: Int,
    val floor: Int
)
