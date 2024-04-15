package ru.itis.loverushka_app.domain.usecase


interface AddDishToFavouritesUseCase {
    suspend operator fun invoke(phoneNumber: String, dishId: Int) : Boolean
}