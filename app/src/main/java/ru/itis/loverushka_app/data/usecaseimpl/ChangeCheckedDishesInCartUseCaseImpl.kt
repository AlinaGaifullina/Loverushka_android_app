package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.repository.CartRepository
import ru.itis.loverushka_app.domain.usecase.ChangeCheckedDishesInCartUseCase

class ChangeCheckedDishesInCartUseCaseImpl(
    private val cartRepository: CartRepository
) : ChangeCheckedDishesInCartUseCase {

    override suspend fun invoke(phoneNumber: String, checkedDishes: List<Int>): Boolean {
        return try {
            cartRepository.updateCheckedDishes(phoneNumber, checkedDishes)
            true
        } catch (e: Exception) {
            false
        }
    }
}