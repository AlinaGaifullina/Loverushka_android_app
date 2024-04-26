package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.data.entity.FavouritesEntity
import ru.itis.loverushka_app.domain.repository.DishRepository
import ru.itis.loverushka_app.domain.usecase.DeleteDishFromFavouritesUseCase

class DeleteDishFromFavouritesUseCaseImpl(
    private val dishRepository: DishRepository,
) : DeleteDishFromFavouritesUseCase {

    override suspend fun invoke(phoneNumber: String, dishId: Int) : Boolean {
        return try {
            dishRepository.deleteDishFromFavourites(phoneNumber, dishId)
        } catch (e: Exception) {
            false
        }
    }
}