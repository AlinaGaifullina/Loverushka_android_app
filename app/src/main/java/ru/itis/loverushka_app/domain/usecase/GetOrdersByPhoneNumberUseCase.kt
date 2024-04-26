package ru.itis.loverushka_app.domain.usecase

import ru.itis.loverushka_app.domain.model.Favourites
import ru.itis.loverushka_app.domain.model.Order

interface GetOrdersByPhoneNumberUseCase {

    suspend operator fun invoke(phoneNumber: String): List<Order>
}