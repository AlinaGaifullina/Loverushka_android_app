package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.repository.DishRepository
import ru.itis.loverushka_app.domain.usecase.AddDishToFavouritesUseCase

class AddDishToFavouritesUseCaseImpl (
    private val dishRepository: DishRepository,
) : AddDishToFavouritesUseCase {
    override suspend fun invoke(phoneNumber: String, dishId: Int) : Boolean {
        return try {
            dishRepository.addDishToFavourites(phoneNumber, dishId)
            true
        } catch (e: Exception){
            false
        }
    }
}