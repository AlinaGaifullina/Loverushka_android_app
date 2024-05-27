package ru.itis.loverushka_app.data.repositotyimpl

import ru.itis.loverushka_app.data.CartDatabase
import ru.itis.loverushka_app.data.entity.CartEntity
import ru.itis.loverushka_app.data.mappers.toCart
import ru.itis.loverushka_app.data.mappers.toCartEntity
import ru.itis.loverushka_app.domain.model.Cart
import ru.itis.loverushka_app.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDb: CartDatabase
) : CartRepository {

    override suspend fun getCartById(cartId: Int): Cart {
        return cartDb.cartDao().getCartById(cartId).toCart()
    }

    override suspend fun getCartByPhoneNumber(phoneNumber: String): Cart {
        return cartDb.cartDao().getCartByPhoneNumber(phoneNumber).toCart()
    }

    override suspend fun addCart(phoneNumber: String, address: String) {
        val address1 = 1
        return cartDb.cartDao().insertCart(
            CartEntity(
                phoneNumber = phoneNumber,
                addressId = address1,
                dishes = listOf(),
                price = 0,
                checkedDishes = listOf(),
                numberOfDishes = listOf()
            )
        )
    }

    override suspend fun updateNumberOfDishes(phoneNumber: String, newNumberOfDishes: List<Int>) {
        return cartDb.cartDao().updateNumberOfDishes(phoneNumber, newNumberOfDishes)
    }

    override suspend fun updateCheckedDishes(phoneNumber: String, checkedDishes: List<Int>) {
        return cartDb.cartDao().updateCheckedDishes(phoneNumber, checkedDishes)
    }

    override suspend fun deleteDishFromCart(phoneNumber: String, newDishesId: List<Int>, newNumberOfDishes: List<Int>) {
        return cartDb.cartDao().deleteDishFromCart(phoneNumber, newDishesId, newNumberOfDishes)
    }

    override suspend fun addDishToCart(phoneNumber: String, newDishesId: List<Int>, newNumberOfDishes: List<Int>) {
        return cartDb.cartDao().addDishToCart(phoneNumber, newDishesId, newNumberOfDishes)
    }

    override suspend fun deleteCart(cart: Cart) {
        return cartDb.cartDao().deleteCart(cart.toCartEntity())
    }
}