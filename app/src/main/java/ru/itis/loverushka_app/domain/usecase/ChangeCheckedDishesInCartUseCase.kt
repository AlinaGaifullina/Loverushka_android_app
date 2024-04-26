package ru.itis.loverushka_app.domain.usecase

interface ChangeCheckedDishesInCartUseCase {

    suspend operator fun invoke(phoneNumber: String, checkedDishes: List<Int>) : Boolean
}