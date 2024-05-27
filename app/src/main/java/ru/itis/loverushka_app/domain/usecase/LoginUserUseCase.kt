package ru.itis.loverushka_app.domain.usecase

import ru.itis.loverushka_app.domain.model.results.LoginResult

interface LoginUserUseCase {

    suspend operator fun invoke(phoneNumber: String, password: String): LoginResult
}