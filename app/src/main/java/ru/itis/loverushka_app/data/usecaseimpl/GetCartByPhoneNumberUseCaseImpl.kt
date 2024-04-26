package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.model.Cart
import ru.itis.loverushka_app.domain.repository.CartRepository
import ru.itis.loverushka_app.domain.usecase.GetCartByPhoneNumberUseCase

class GetCartByPhoneNumberUseCaseImpl(
    private val cartRepository: CartRepository
) : GetCartByPhoneNumberUseCase {
    override suspend fun invoke(phoneNumber: String): Cart {
        return cartRepository.getCartByPhoneNumber(phoneNumber)
    }
}