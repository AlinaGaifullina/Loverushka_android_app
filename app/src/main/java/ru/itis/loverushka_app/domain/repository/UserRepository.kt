package ru.itis.loverushka_app.domain.repository

import ru.itis.loverushka_app.domain.model.User

interface UserRepository {

    suspend fun getUserByNumber(phoneNumber: String) : User?
    suspend fun createUser(user: User)
}