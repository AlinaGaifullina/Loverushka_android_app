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

    @Query("select * from carts where userPhoneNumber = :phoneNumber")
    suspend fun getCartByPhoneNumber(phoneNumber: String): CartEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity)

    @Delete
    suspend fun deleteCart(cart: CartEntity)
}