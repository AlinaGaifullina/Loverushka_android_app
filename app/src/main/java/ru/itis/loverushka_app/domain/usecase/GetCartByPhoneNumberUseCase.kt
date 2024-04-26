package ru.itis.loverushka_app.domain.usecase

import ru.itis.loverushka_app.domain.model.Cart
import ru.itis.loverushka_app.domain.model.Dish

interface GetCartByPhoneNumberUseCase {

    suspend operator fun invoke(phoneNumber: String): Cart
}