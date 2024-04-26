package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.model.Order
import ru.itis.loverushka_app.domain.repository.OrderRepository
import ru.itis.loverushka_app.domain.usecase.GetOrdersByPhoneNumberUseCase

class GetOrdersByPhoneNumberUseCaseImpl(
    private val orderRepository: OrderRepository
) : GetOrdersByPhoneNumberUseCase {
    override suspend fun invoke(phoneNumber: String): List<Order> {
        return orderRepository.getOrdersByPhoneNumber(phoneNumber)
    }
}