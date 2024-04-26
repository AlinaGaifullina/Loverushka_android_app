package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.repository.CartRepository
import ru.itis.loverushka_app.domain.usecase.DeleteDishFromCartUseCase

class DeleteDishFromCartUseCaseImpl(
    private val cartRepository: CartRepository
) : DeleteDishFromCartUseCase {

    override suspend fun invoke(phoneNumber: String, newDishesId: List<Int>, newNumberOfDishes: List<Int>): Boolean {
        return try {
            cartRepository.deleteDishFromCart(phoneNumber, newDishesId, newNumberOfDishes)
            true
        } catch (e: Exception){
            false
        }
    }
}