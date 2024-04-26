package ru.itis.loverushka_app.ui.screens.register

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.itis.loverushka_app.domain.model.RegisterResult
import ru.itis.loverushka_app.domain.usecase.AddCartUseCase
import ru.itis.loverushka_app.domain.usecase.RegisterUserUseCase
import javax.inject.Inject


data class RegisterState(
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val showLoadingProgressBar: Boolean = false,
    val showErrors: Boolean = false,
    val loginError: String = "",
    val errors: List<String> = listOf()
)

sealed interface RegisterSideEffect {
    object NavigateProfile : RegisterSideEffect
    object NavigateLogin : RegisterSideEffect
}

sealed interface RegisterEvent {
    object OnRegisterButtonClick : RegisterEvent
    object OnLoginButtonCLick : RegisterEvent
    object OnPasswordVisibilityChange : RegisterEvent
    data class OnUsernameChange(val value: String) : RegisterEvent
    data class OnFirstNameChange(val value: String) : RegisterEvent
    data class OnLastNameChange(val value: String) : RegisterEvent
    data class OnPasswordChange(val value: String) : RegisterEvent
    data class OnConfirmPasswordChange(val value: String) : RegisterEvent
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    //private val isUsernameExist: CheckUsernameUseCase,
    private val register: RegisterUserUseCase,
    private val addCartUseCase: AddCartUseCase
) : ViewModel(){
    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(
        RegisterState()
    )
    val state: StateFlow<RegisterState> = _state

    private val _action = MutableSharedFlow<RegisterSideEffect?>()
    val action: SharedFlow<RegisterSideEffect?>
        get() = _action.asSharedFlow()

    fun event(event: RegisterEvent) {
        when (event) {
            RegisterEvent.OnRegisterButtonClick -> onRegisterButtonClick()
            RegisterEvent.OnLoginButtonCLick -> onLoginButtonClick()
            RegisterEvent.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
            is RegisterEvent.OnUsernameChange -> onUsernameChange(event.value)
            is RegisterEvent.OnFirstNameChange -> onFirstNameChange(event.value)
            is RegisterEvent.OnLastNameChange -> onLastNameChange(event.value)
            is RegisterEvent.OnPasswordChange -> onPasswordChange(event.value)
            is RegisterEvent.OnConfirmPasswordChange -> onConfirmPasswordChange(event.value)
        }
    }

    private fun onUsernameChange(phone: String) {
        _state.tryEmit(_state.value.copy(phone = phone))
    }

    private fun onFirstNameChange(firstName: String) {
        _state.tryEmit(_state.value.copy(firstName = firstName))
    }

    private fun onLastNameChange(lastName: String) {
        _state.tryEmit(_state.value.copy(lastName = lastName))
    }

    private fun onPasswordChange(password: String) {
        _state.tryEmit(_state.value.copy(password = password))
    }

    private fun onConfirmPasswordChange(password: String) {
        _state.tryEmit(_state.value.copy(confirmPassword = password))
    }

    private fun onPasswordVisibilityChange() {
        _state.tryEmit(_state.value.copy(passwordVisible = !_state.value.passwordVisible))
    }


    private fun onLoginButtonClick() {
        viewModelScope.launch {
            _action.emit(RegisterSideEffect.NavigateLogin)
        }
    }

    private fun onRegisterButtonClick() {
        viewModelScope.launch {
            val errors = mutableListOf<String>()
            _state.emit(_state.value.copy(showLoadingProgressBar = true))
            val result =
                if (validateFields(errors)) with(state.value) {
                    register(firstName, lastName, phone, password)
                }
                else RegisterResult.FailRegister()
            _state.emit(_state.value.copy(
                showLoadingProgressBar = false,
                errors = errors )
            )

            when (result) {
                is RegisterResult.SuccessRegister -> {
                    addCartUseCase(_state.value.phone, "г. Казань, ул. Черноморская, д. 5, кв. 42")
                    _action.emit(RegisterSideEffect.NavigateProfile)
                }

                is RegisterResult.FailRegister -> {
                    result.errorMessage?.let { errors.add(it) }
                    _state.emit(_state.value.copy(showErrors = true, errors = errors))
                }
            }
        }
    }


    private fun validateFields(errors: MutableList<String>): Boolean {
//        if (state.value.phone.length < 3) {
//            errors.add("Логин должен состоять не менее чем из 3 символов.")
//            return false
//        }

        var passValidate = true
        val password = state.value.password
        if (!password.matches(Regex(".*[a-z].*"))) {
            errors.add("Пароль должен содержать хотя бы одну маленькую букву.")
            passValidate = false
        }

        if (!password.matches(Regex(".*[A-Z].*"))) {
            errors.add("Пароль должен содержать хотя бы одну большую букву.")
            passValidate = false
        }

        if (!password.matches(Regex(".*\\d.*"))) {
            passValidate = false
            errors.add("Пароль должен содержать хотя бы одну цифру.")
        }

        if (password.length < 8) {
            passValidate = false
            errors.add("Пароль должен содержать минимум 8 символов.")
        }

        if (password != state.value.confirmPassword) {
            passValidate = false
            errors.add("Пароли не совпадают.")
        }

        return passValidate
    }
}