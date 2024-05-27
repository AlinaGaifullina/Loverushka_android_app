package ru.itis.loverushka_app.domain.model.results

sealed interface RegisterResult {
    data class SuccessRegister(val phoneNumber: String) : RegisterResult
    data class FailRegister(val errorMessage: String? = null) : RegisterResult
}