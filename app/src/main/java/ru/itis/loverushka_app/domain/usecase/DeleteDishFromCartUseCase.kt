package ru.itis.loverushka_app.domain.usecase

interface DeleteDishFromCartUseCase {

    suspend operator fun invoke(phoneNumber: String, newDishesId: List<Int>, newNumberOfDishes: List<Int>) : Boolean
}