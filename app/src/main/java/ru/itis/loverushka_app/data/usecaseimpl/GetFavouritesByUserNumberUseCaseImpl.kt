package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.data.mappers.toFavourites
import ru.itis.loverushka_app.domain.model.Favourites
import ru.itis.loverushka_app.domain.repository.DishRepository
import ru.itis.loverushka_app.domain.usecase.GetFavouritesByUserNumberUseCase

class GetFavouritesByUserNumberUseCaseImpl (
    private val dishRepository: DishRepository,
) : GetFavouritesByUserNumberUseCase {
    override suspend fun invoke(phoneNumber: String): List<Favourites> {
        return dishRepository.getFavouritesByUserNumber(phoneNumber).map { it.toFavourites() }
    }

}