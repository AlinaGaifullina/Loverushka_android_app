package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.model.LoginResult
import ru.itis.loverushka_app.domain.repository.UserRepository
import ru.itis.loverushka_app.domain.usecase.LoginUserUseCase

class LoginUserUseCaseImpl(
    private val userRepository: UserRepository,
) : LoginUserUseCase {

    override suspend fun invoke(phoneNumber: String, password: String): LoginResult {
        return try {

            val user = userRepository.getUserByNumber(phoneNumber)
            if(password == user?.password){
                LoginResult.SuccessLogin(phoneNumber)
            } else LoginResult.FailLogin("Неправильный пароль")
        } catch (e: java.lang.Exception) {
            LoginResult.FailLogin("Пользователь не найден")
        }
    }
}