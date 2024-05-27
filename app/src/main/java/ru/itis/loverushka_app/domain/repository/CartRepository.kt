package ru.itis.loverushka_app.domain.repository
import androidx.room.Delete
import androidx.room.Query
import ru.itis.loverushka_app.domain.model.Cart

interface CartRepository {

    suspend fun getCartById(cartId: Int): Cart
    suspend fun getCartByPhoneNumber(phoneNumber: String): Cart
    suspend fun addCart(phoneNumber: String, address: String)
    suspend fun updateNumberOfDishes(phoneNumber: String, newNumberOfDishes: List<Int>)
    suspend fun updateCheckedDishes(phoneNumber: String, checkedDishes: List<Int>)
    suspend fun deleteDishFromCart(phoneNumber: String, newDishesId: List<Int>, newNumberOfDishes: List<Int>);
    suspend fun addDishToCart(phoneNumber: String, newDishesId: List<Int>, newNumberOfDishes: List<Int>);
    suspend fun deleteCart(cart: Cart)
}