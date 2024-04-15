package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.model.Dish
import ru.itis.loverushka_app.domain.repository.DishRepository
import ru.itis.loverushka_app.domain.usecase.GetAllDishesUseCase

class GetAllDishesUseCaseImpl (
    private val dishRepository: DishRepository,
) : GetAllDishesUseCase {

    override suspend fun invoke(): List<Dish> {
        return dishRepository.getAllDishes()
    }
}