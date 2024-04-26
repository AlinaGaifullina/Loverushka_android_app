package ru.itis.loverushka_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    val firstName: String,
    val lastName: String,

    @PrimaryKey
    val phoneNumber: String,

    val password: String,

    val country: String,
    val city: String,
    val cartId: Int,
    val ordersId: List<Int>
)