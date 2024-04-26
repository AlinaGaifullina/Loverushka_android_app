package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.repository.CartRepository
import ru.itis.loverushka_app.domain.usecase.ChangeNumberOfDishInCartUseCase

class ChangeNumberOfDishInCartUseCaseImpl(
    private val cartRepository: CartRepository
) : ChangeNumberOfDishInCartUseCase {

    override suspend fun invoke(phoneNumber: String, newNumberOfDishes: List<Int>): Boolean {
        return try {
            cartRepository.updateNumberOfDishes(phoneNumber, newNumberOfDishes)
            true
        } catch (e: Exception) {
            false
        }
    }
}