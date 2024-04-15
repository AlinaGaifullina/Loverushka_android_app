package ru.itis.loverushka_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int,
    val userPhoneNumber: String,
    val address: String,
    val dishes: List<Int>,
    val numberOfDishes: List<Int>,
    val price: Int,
    val status: String,
    val date: String,
    val payWay: String
)