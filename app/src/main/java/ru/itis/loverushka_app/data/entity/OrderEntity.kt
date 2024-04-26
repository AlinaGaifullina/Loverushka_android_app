package ru.itis.loverushka_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 1,
    val userPhoneNumber: String,
    val address: String,
    val dishes: List<Int>,
    val numberOfDishes: List<Int>,
    val price: Int,
    val status: String,
    val date: String,
    val time: String,
    val payWay: String
)