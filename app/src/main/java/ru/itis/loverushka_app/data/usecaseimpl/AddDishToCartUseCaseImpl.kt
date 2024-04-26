package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.repository.CartRepository
import ru.itis.loverushka_app.domain.usecase.AddDishToCartUseCase

class AddDishToCartUseCaseImpl(
    private val cartRepository: CartRepository
) : AddDishToCartUseCase {
    override suspend fun invoke(phoneNumber: String, newDishesId: List<Int>, newNumberOfDishes: List<Int>): Boolean {
        return try {
            cartRepository.addDishToCart(phoneNumber, newDishesId, newNumberOfDishes)
            true
        } catch (e: Exception){
            false
        }
    }
}