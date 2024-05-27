package ru.itis.loverushka_app.data.repositotyimpl

import ru.itis.loverushka_app.data.OrderDatabase
import ru.itis.loverushka_app.data.entity.OrderEntity
import ru.itis.loverushka_app.data.mappers.toOrder
import ru.itis.loverushka_app.domain.model.Order
import ru.itis.loverushka_app.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDb: OrderDatabase
): OrderRepository {
    override suspend fun getOrderById(orderId: Int): Order {
        return orderDb.orderDao().getOrderById(orderId).toOrder()
    }

    override suspend fun getOrdersByPhoneNumber(phoneNumber: String): List<Order> {
        return orderDb.orderDao().getOrdersByPhoneNumber(phoneNumber).map { it.toOrder() }
    }

    override suspend fun addOrder(
        phoneNumber: String,
        address: String,
        dishes: List<Int>,
        numberOfDishes: List<Int>,
        price: Int,
        status: String,
        date: String,
        time: String,
        payWay: String
    ) {
        orderDb.orderDao().insertOrder(
            OrderEntity(
                phoneNumber = phoneNumber,
                address = address,
                dishes = dishes,
                numberOfDishes = numberOfDishes,
                price = price,
                status = status,
                date = date,
                time = time,
                payWay = payWay
            )
        )
    }
}