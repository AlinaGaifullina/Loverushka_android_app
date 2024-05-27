package ru.itis.loverushka_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.loverushka_app.data.entity.CartEntity

@Dao
interface CartDao {

    @Query("select * from carts where cartId = :cartId")
    suspend fun getCartById(cartId: Int): CartEntity

    @Query("select * from carts where phoneNumber = :phoneNumber")
    suspend fun getCartByPhoneNumber(phoneNumber: String): CartEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity)

    @Query("UPDATE carts SET numberOfDishes = :newNumberOfDishes WHERE phoneNumber = :phoneNumber")
    suspend fun updateNumberOfDishes(phoneNumber: String, newNumberOfDishes: List<Int>)
    @Query("UPDATE carts SET checkedDishes = :checkedDishes WHERE phoneNumber = :phoneNumber")
    suspend fun updateCheckedDishes(phoneNumber: String, checkedDishes: List<Int>)

    @Query("UPDATE carts SET dishes = :newDishesId, numberOfDishes = :newNumberOfDishes WHERE phoneNumber = :phoneNumber")
    suspend fun deleteDishFromCart(phoneNumber: String, newDishesId: List<Int>, newNumberOfDishes: List<Int>);

    @Query("UPDATE carts SET dishes = :newDishesId, numberOfDishes = :newNumberOfDishes WHERE phoneNumber = :phoneNumber")
    suspend fun addDishToCart(phoneNumber: String, newDishesId: List<Int>, newNumberOfDishes: List<Int>);
    @Delete
    suspend fun deleteCart(cart: CartEntity)
}