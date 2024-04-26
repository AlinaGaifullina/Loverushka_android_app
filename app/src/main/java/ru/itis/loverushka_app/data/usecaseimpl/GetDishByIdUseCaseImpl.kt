package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.model.Dish
import ru.itis.loverushka_app.domain.repository.DishRepository
import ru.itis.loverushka_app.domain.usecase.GetDishByIdUseCase
import ru.itis.loverushka_app.domain.usecase.GetFavouritesByUserNumberUseCase

class GetDishByIdUseCaseImpl (
    private val dishRepository: DishRepository,
) : GetDishByIdUseCase {
    override suspend fun invoke(dishId: Int): Dish {
        return dishRepository.getDishById(dishId)
    }
}