package ru.itis.loverushka_app.data.repositotyimpl

import ru.itis.loverushka_app.data.DishDatabase
import ru.itis.loverushka_app.data.FavouritesDatabase
import ru.itis.loverushka_app.data.entity.FavouritesEntity
import ru.itis.loverushka_app.data.mappers.toDish
import ru.itis.loverushka_app.data.mappers.toDishEntity
import ru.itis.loverushka_app.domain.model.Dish
import ru.itis.loverushka_app.domain.repository.DishRepository
import javax.inject.Inject


class DishRepositoryImpl @Inject constructor(
    private val dishDb: DishDatabase,
    private val favouritesDb: FavouritesDatabase
): DishRepository {


    override suspend fun getDishByName(name: String): Dish {
        return dishDb.dishDao().getDishByName(name).toDish()
    }

    override suspend fun addDishToFavourites(phoneNumber: String, dishId: Int) : Boolean {
        return try {
            favouritesDb.favouritesDao().insertFavourites( FavouritesEntity(phoneNumber = phoneNumber, dishId = dishId))
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getFavouritesByUserNumber(phoneNumber: String): List<FavouritesEntity> {
        return favouritesDb.favouritesDao().getFavouritesByPhoneNumber(phoneNumber)
    }

    override suspend fun getAllDishes() : List<Dish> {
        return dishDb.dishDao().getAllDishes().map { dishEntity -> dishEntity.toDish() }
    }

    override suspend fun getDishById(dishId: Int) : Dish {
        return  dishDb.dishDao().getDishById(dishId).toDish()
    }

    override suspend fun addDish(dish: Dish): Boolean {
        return try {
            dishDb.dishDao().insertDish(dish.toDishEntity())
            true
        } catch (e: Exception) {
            false
        }
    }

//    override suspend fun getFavouritesById(favouritesId: Int): FavouritesCore {
//        return favouritesDb.favouritesCoreDao().getFavouritesById(favouritesId)
//    }
//
//    override suspend fun getFavouritesByUsername(username: String): List<FavouritesCore> {
//        return favouritesDb.favouritesCoreDao().getFavouritesByUsername(username)
//    }

    override suspend fun deleteDishFromFavourites(phoneNumber: String, dishId: Int) : Boolean {
        return try {
            favouritesDb.favouritesDao().deleteFavourites(phoneNumber, dishId)
            true
        } catch (e: Exception) {
            false
        }
    }
}