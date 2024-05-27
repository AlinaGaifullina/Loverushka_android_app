package ru.itis.loverushka_app.data.mappers

import ru.itis.loverushka_app.data.entity.DishEntity
import ru.itis.loverushka_app.data.entity.FavouritesEntity
import ru.itis.loverushka_app.domain.model.Dish
import ru.itis.loverushka_app.domain.model.Favourites

fun Dish.toDishEntity(): DishEntity =
    DishEntity(dishId, dishName, description, photo, authorId, price, ingredients)

fun DishEntity.toDish(): Dish =
    Dish(dishId, name, description, photo, author, price, ingredients)

fun Favourites.toFavouritesEntity(): FavouritesEntity =
    FavouritesEntity(dishId, phoneNumber, dishId)

fun FavouritesEntity.toFavourites(): Favourites =
    Favourites(dishId, phoneNumber, dishId)