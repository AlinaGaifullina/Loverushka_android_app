package ru.itis.loverushka_app.domain.usecase

import ru.itis.loverushka_app.domain.model.Favourites

interface GetFavouritesByUserNumberUseCase {

    suspend operator fun invoke(phoneNumber: String): List<Favourites>
}