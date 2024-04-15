package ru.itis.loverushka_app.domain.model

sealed interface LoginResult {
    data class SuccessLogin(val phoneNumber: String) : LoginResult
    data class FailLogin(val errorMessage: String? = null) : LoginResult
}