package ru.itis.loverushka_app.domain.usecase

import ru.itis.loverushka_app.data.entity.FavouritesEntity

interface DeleteDishFromFavouritesUseCase {
    suspend operator fun invoke(favourites: FavouritesEntity) : Boolean
}