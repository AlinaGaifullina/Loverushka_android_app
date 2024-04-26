package ru.itis.loverushka_app.domain.usecase

import ru.itis.loverushka_app.domain.model.Order

interface GetOrderByIdUseCase {

    suspend operator fun invoke(orderId: Int): Order
}