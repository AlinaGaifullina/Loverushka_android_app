package ru.itis.loverushka_app.domain.usecase

import ru.itis.loverushka_app.domain.model.Dish

interface GetAllDishesUseCase {
    suspend operator fun invoke(): List<Dish>
}