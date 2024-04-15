package ru.itis.loverushka_app.data.usecaseimpl

import ru.itis.loverushka_app.domain.model.RegisterResult
import ru.itis.loverushka_app.domain.model.User
import ru.itis.loverushka_app.domain.repository.UserRepository
import ru.itis.loverushka_app.domain.usecase.RegisterUserUseCase
import javax.inject.Inject
import kotlin.random.Random

class RegisterUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
) : RegisterUserUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String
    ): RegisterResult {
        return try {
            val hashedPassword = password //TODO
            val cartNumber = Random(454545).nextInt()
            userRepository.createUser(User(firstName, lastName, phoneNumber, hashedPassword, "", "", cartNumber, listOf()))
            RegisterResult.SuccessRegister(phoneNumber)
        } catch (e: Exception) {
            RegisterResult.FailRegister(e.message)
        }
    }
}