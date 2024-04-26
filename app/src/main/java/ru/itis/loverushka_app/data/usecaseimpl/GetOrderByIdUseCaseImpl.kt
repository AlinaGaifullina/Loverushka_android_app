package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.model.Order
import ru.itis.loverushka_app.domain.repository.OrderRepository
import ru.itis.loverushka_app.domain.usecase.GetOrderByIdUseCase

class GetOrderByIdUseCaseImpl(
    private val orderRepository: OrderRepository
) : GetOrderByIdUseCase {
    override suspend fun invoke(orderId: Int): Order {
        return orderRepository.getOrderById(orderId)
    }
}