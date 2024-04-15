package ru.itis.loverushka_app.data.mappers

import ru.itis.loverushka_app.data.entity.DishEntity
import ru.itis.loverushka_app.domain.model.Dish

fun Dish.toDishEntity(): DishEntity =
    DishEntity(dishId, dishName, dishDescription, dishPhoto, dishAuthor, dishPrice, ingredients)

fun DishEntity.toDish(): Dish =
    Dish(dishId, dishName, dishDescription, dishPhoto, dishAuthor, dishPrice, ingredients)