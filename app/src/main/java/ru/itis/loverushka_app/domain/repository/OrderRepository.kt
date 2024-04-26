package ru.itis.loverushka_app.domain.repository

import ru.itis.loverushka_app.domain.model.Order

interface OrderRepository {

    suspend fun getOrderById(orderId: Int): Order

    suspend fun getOrdersByPhoneNumber(phoneNumber: String): List<Order>

    suspend fun addOrder(
        phoneNumber: String,
        address: String,
        dishes: List<Int>,
        numberOfDishes: List<Int>,
        price: Int,
        status: String,
        date: String,
        time: String,
        payWay: String
    )
}