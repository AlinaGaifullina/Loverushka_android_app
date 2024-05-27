package ru.itis.loverushka_app.domain.repository

import ru.itis.loverushka_app.data.entity.FavouritesEntity
import ru.itis.loverushka_app.domain.model.Dish

interface DishRepository {

    suspend fun getDishByName(name: String) : Dish
    suspend fun getAllDishes() : List<Dish>
    suspend fun getDishById(dishId: Int) : Dish
    suspend fun addDish(dish: Dish) : Boolean

    suspend fun getFavouritesByUserNumber(phoneNumber: String) : List<FavouritesEntity>
    suspend fun addDishToFavourites(phoneNumber: String, dishId: Int) : Boolean
    suspend fun deleteDishFromFavourites(phoneNumber: String, dishId: Int) : Boolean
}