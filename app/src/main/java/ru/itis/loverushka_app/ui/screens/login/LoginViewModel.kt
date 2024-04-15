package ru.itis.loverushka_app.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.itis.loverushka_app.domain.model.Dish
import ru.itis.loverushka_app.domain.model.LoginResult
import ru.itis.loverushka_app.domain.repository.DishRepository
import ru.itis.loverushka_app.domain.usecase.GetAllDishesUseCase
import ru.itis.loverushka_app.domain.usecase.LoginUserUseCase
import javax.inject.Inject

data class LoginState(
    val phone: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val showLoadingProgressBar: Boolean = false,
    val showErrorDialog: Boolean = false,
    val showLoginError: Boolean = false,
    val loginError: String = "",
    val errors: List<String> = listOf()
)

sealed interface LoginSideEffect {
    object NavigateProfile : LoginSideEffect
    object NavigateRegister : LoginSideEffect
}

sealed interface LoginEvent {
    object OnLoginButtonClick : LoginEvent
    object OnRegisterButtonCLick : LoginEvent
    object OnPasswordVisibilityChange : LoginEvent
    data class OnUsernameChange(val value: String) : LoginEvent
    data class OnPasswordChange(val value: String) : LoginEvent
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUserUseCase,
    private val dishRep: DishRepository
) : ViewModel(){
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _action = MutableSharedFlow<LoginSideEffect?>()
    val action: SharedFlow<LoginSideEffect?>
        get() = _action.asSharedFlow()

    fun event(event: LoginEvent) {
        when (event) {
            LoginEvent.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
            is LoginEvent.OnUsernameChange -> onUsernameChange(event.value)
            is LoginEvent.OnPasswordChange -> onPasswordChange(event.value)
            LoginEvent.OnLoginButtonClick -> onLoginButtonClick()
            LoginEvent.OnRegisterButtonCLick -> onRegisterButtonCLick()
        }
    }

    private fun onUsernameChange(phone: String) {
        _state.tryEmit(_state.value.copy(phone = phone, showLoginError = false))
    }

    private fun onPasswordChange(password: String) {
        _state.tryEmit(_state.value.copy(password = password, showLoginError = false))
    }

    private fun onPasswordVisibilityChange() {
        _state.tryEmit(_state.value.copy(passwordVisible = !_state.value.passwordVisible))
    }

    private fun onRegisterButtonCLick() {
        viewModelScope.launch { _action.emit(LoginSideEffect.NavigateRegister) }
    }

    private fun onLoginButtonClick() {
        var loginError = ""
        viewModelScope.launch {
            val errors = mutableListOf<String>()
            _state.emit(_state.value.copy(showLoadingProgressBar = true))
            val result = if (validateFields(errors)) with(state.value) {
                login(phone, password)
            }
            else LoginResult.FailLogin()
            _state.emit(_state.value.copy(showLoadingProgressBar = false))

            when (result) {
                is LoginResult.SuccessLogin -> {
//                    dishRep.addDish(
//                        Dish(1,
//                        "Мамин борщ",
//                        "Борщ — разновидность супа на основе свёклы, которая придаёт ему характерный красно-коричневый цвет. Традиционная пища восточных славян, популярное блюдо в русской кухне, основное первое блюдо украинской кухни. Блюдо имеет около дюжины разновидностей, получило широкое распространение в национальных кухнях соседних народов",
//                        "https://proprikol.ru/wp-content/uploads/2020/04/kartinki-borshh-6.jpg",
//                        "Анна Картошкина",
//                        350,
//                        "свекла, белокочанная капуста, морковь, корень петрушки, картофель, лук, помидоры")
//                    )
//
//                    dishRep.addDish(
//                        Dish(2,
//                            "Бабушкины пирожки",
//                            "Пирожки — распространенное кондитерское изделие из сдобного дрожжевого или слоеного теста с разнообразными начинками. Пирожки могут быть с мясными начинками, например, из свинины, а также из рыбы, курицы. Сладкие начинки могут быть из фруктов (яблоки, вишня, абрикосы, лимон и так далее), творога, варенья или джема. Самые популярные пирожки — с овощами: из капусты или картофеля.",
//                            "https://mykaleidoscope.ru/x/uploads/posts/2022-09/1663637067_12-mykaleidoscope-ru-p-pirozhki-s-myasom-yeda-krasivo-14.jpg",
//                            "Антонина Александровна",
//                            270,
//                            "яйца, сливочное масло, пшеничная мука, дрожжи, капуста, соль")
//                    )
//
//                    dishRep.addDish(
//                        Dish(3,
//                            "Шоколадные кексы",
//                            "Кексы - это кондитерские изделия, которые готовят путем выпекания масляного бисквитного или дрожжевого теста. Общие сведения о кексах и технологии их приготовления. Ярким представителем группы сдобных кондитерских изделий является кекс. Кекс представляет собой высококалорийное кондитерское изделие, которое получают из сдобного теста, содержащего большое количество жира, яйцепродуктов и сахара.",
//                            "https://mykaleidoscope.ru/x/uploads/posts/2022-10/1666387966_13-mykaleidoscope-ru-p-turetskii-shokoladnii-keks-vkontakte-14.jpg",
//                            "Андрей Петров",
//                            300,
//                            "сахар, молоко, какао, ванильный сахар, сливочное масло, пшеничная мука,")
//                    )
                    _action.emit(LoginSideEffect.NavigateProfile)
                }

                is LoginResult.FailLogin -> {
                    result.errorMessage?.let { loginError = it }
                    _state.emit(_state.value.copy(showLoginError = true, loginError = loginError))
                }
            }
        }
    }

    private fun validateFields(errors: MutableList<String>): Boolean {

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

        return passValidate
    }
}