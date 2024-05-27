package ru.itis.loverushka_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.loverushka_app.data.entity.DishEntity

@Dao
interface DishDao{

    @Query("select * from dishes where name = :dishName")
    suspend fun getDishByName(dishName: String): DishEntity

    @Query("select * from dishes where dishId = :dishId")
    suspend fun getDishById(dishId: Int): DishEntity

    @Query("select * from dishes")
    suspend fun getAllDishes(): List<DishEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDish(dish: DishEntity)

    @Delete
    suspend fun deleteDish(dish: DishEntity)

}