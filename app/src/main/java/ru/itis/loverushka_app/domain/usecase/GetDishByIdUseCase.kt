package ru.itis.loverushka_app.domain.usecase

import ru.itis.loverushka_app.domain.model.Dish

interface GetDishByIdUseCase {

    suspend operator fun invoke(dishId: Int): Dish
}