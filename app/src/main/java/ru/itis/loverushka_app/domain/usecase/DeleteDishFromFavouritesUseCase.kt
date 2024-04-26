package ru.itis.loverushka_app.domain.usecase

import ru.itis.loverushka_app.data.entity.FavouritesEntity
import ru.itis.loverushka_app.domain.model.Favourites

interface DeleteDishFromFavouritesUseCase {
    suspend operator fun invoke(phoneNumber: String, dishId: Int) : Boolean
}