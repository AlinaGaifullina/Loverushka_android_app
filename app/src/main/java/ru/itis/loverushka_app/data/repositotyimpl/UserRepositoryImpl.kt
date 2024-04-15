package ru.itis.loverushka_app.data.repositotyimpl

import ru.itis.loverushka_app.data.UserDatabase
import ru.itis.loverushka_app.data.mappers.toUser
import ru.itis.loverushka_app.data.mappers.toUserEntity
import ru.itis.loverushka_app.domain.model.User
import ru.itis.loverushka_app.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: UserDatabase,
): UserRepository {

    override suspend fun createUser(user: User) {
        return db.userDao().insertUser(user.toUserEntity())
    }

    override suspend fun getUserByNumber(phoneNumber: String): User {
        return db.userDao().getUserByNumber(phoneNumber).toUser()
    }
}