package ru.itis.loverushka_app.data.mappers

import ru.itis.loverushka_app.data.entity.CartEntity
import ru.itis.loverushka_app.data.entity.OrderEntity
import ru.itis.loverushka_app.data.entity.UserEntity
import ru.itis.loverushka_app.domain.model.Cart
import ru.itis.loverushka_app.domain.model.Order
import ru.itis.loverushka_app.domain.model.User

fun User.toUserEntity(): UserEntity =
    UserEntity(firstName, lastName, phoneNumber, password, country, city, cartId, ordersId)

fun UserEntity.toUser(): User =
    User(firstName, lastName, phoneNumber, password, country, city, cartId, ordersId)

fun Cart.toCartEntity(): CartEntity =
    CartEntity(cartId, userPhoneNumber, address, dishes, price, checkedDishes, numberOfDishes )

fun CartEntity.toCart(): Cart =
    Cart(cartId, userPhoneNumber, address, dishes, price, checkedDishes, numberOfDishes)

fun Order.toOrderEntity(): OrderEntity =
    OrderEntity(orderId, userPhoneNumber, address, dishes, numberOfDishes, price, status, date, time, payWay)

fun OrderEntity.toOrder(): Order =
    Order(orderId, userPhoneNumber, address, dishes, numberOfDishes, price, status, date, time, payWay)