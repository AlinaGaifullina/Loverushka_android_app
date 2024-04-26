package ru.itis.loverushka_app.domain.usecase

interface AddCartUseCase {

    suspend operator fun invoke(phoneNumber: String, address: String) : Boolean
}