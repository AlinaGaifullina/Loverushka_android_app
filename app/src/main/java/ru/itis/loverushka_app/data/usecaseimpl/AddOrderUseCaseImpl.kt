package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.repository.OrderRepository
import ru.itis.loverushka_app.domain.usecase.AddOrderUseCase

class AddOrderUseCaseImpl(
    private val orderRepository: OrderRepository
) : AddOrderUseCase {

    override suspend fun invoke(
        phoneNumber: String,
        address: String,
        dishes: List<Int>,
        numberOfDishes: List<Int>,
        price: Int,
        status: String,
        date: String,
        time: String,
        payWay: String
    ): Boolean {
        return try {
            orderRepository.addOrder(
                phoneNumber,
                address,
                dishes,
                numberOfDishes,
                price,
                status,
                date,
                time,
                payWay
            )
            true
        } catch (e: Exception){
            false
        }
    }
}