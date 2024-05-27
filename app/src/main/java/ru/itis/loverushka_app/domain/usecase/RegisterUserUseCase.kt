package ru.itis.loverushka_app.domain.usecase

import ru.itis.loverushka_app.domain.model.results.RegisterResult

interface RegisterUserUseCase {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String
    ): RegisterResult
}