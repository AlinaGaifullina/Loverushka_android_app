package ru.itis.loverushka_app.domain.usecase

interface AddOrderUseCase {

    suspend operator fun invoke(
        phoneNumber: String,
        address: String,
        dishes: List<Int>,
        numberOfDishes: List<Int>,
        price: Int,
        status: String,
        date: String,
        time: String,
        payWay: String
    ) : Boolean
}