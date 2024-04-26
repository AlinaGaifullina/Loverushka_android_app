package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.repository.CartRepository
import ru.itis.loverushka_app.domain.usecase.AddCartUseCase

class AddCartUseCaseImpl(
    private val cartRepository: CartRepository
) : AddCartUseCase {
    override suspend fun invoke(phoneNumber: String, address: String): Boolean {
        return try {
            cartRepository.addCart(phoneNumber, address)
            true
        } catch (e: Exception){
            false
        }
    }
}