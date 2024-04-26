package ru.itis.loverushka_app.domain.usecase

interface ChangeNumberOfDishInCartUseCase {

    suspend operator fun invoke(phoneNumber: String, newNumberOfDishes: List<Int>) : Boolean
}