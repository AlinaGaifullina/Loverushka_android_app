package ru.itis.loverushka_app.ui.screens.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.itis.loverushka_app.domain.model.Dish
import ru.itis.loverushka_app.domain.model.Order
import ru.itis.loverushka_app.domain.repository.DishRepository
import ru.itis.loverushka_app.domain.repository.UserRepository
import ru.itis.loverushka_app.ui.navigation.ProfileNavScreen
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
data class ProfileState(
//    val isSelfProfile: Boolean = false,
//    val isSubscribe: Boolean = false,
    val userPhone: String = "",
    val userFirstName: String = "",
    val userLastName: String = "",
    val userCity: String? = null,
    val userCountry: String? = null,
    //val userDescription: String = "",
    val userFavourites: List<Dish> = listOf(),
    val userOrders: List<Order> = listOf(),
    val isDropdownMenuExpanded: Boolean = false,
)

sealed interface ProfileSideEffect {
    //object NavigateToEditProfileScreen : ProfileSideEffect
    //object NavigateToLoginScreen : ProfileSideEffect
    data class NavigateToDishDetailsScreen(val id: Int) : ProfileSideEffect
    data class NavigateToOrderDetailsScreen(val id: Int) : ProfileSideEffect
}

sealed interface ProfileEvent {
    object OnLaunch : ProfileEvent
    object OnDispose : ProfileEvent
    object OnDropdownMenuClick : ProfileEvent
    object OnCloseDropdownMenu : ProfileEvent
    data class OnDishClick(val id: Int) : ProfileEvent
    data class OnOrderClick(val id: Int) : ProfileEvent
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    //private val getCurrentUserUseCase: GetCurrentUserUseCase,
    //private val getRoutesByIdListUseCase: GetRoutesByIdListUseCase,
    private val userRep: UserRepository,
    private val dishRep: DishRepository,
) : ViewModel() {


    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _action = MutableSharedFlow<ProfileSideEffect?>()
    val action: SharedFlow<ProfileSideEffect?>
        get() = _action.asSharedFlow()

    private var currentJob: Job? = null

    fun event(profileEvent: ProfileEvent) {
        when (profileEvent) {
            ProfileEvent.OnLaunch -> initState()
            ProfileEvent.OnDispose -> onDispose()
            ProfileEvent.OnDropdownMenuClick -> onDropdownMenuClick()
            ProfileEvent.OnCloseDropdownMenu -> onCloseDropdownMenu()
            is ProfileEvent.OnDishClick -> onDishClick(profileEvent.id)
            is ProfileEvent.OnOrderClick -> onOrderClick(profileEvent.id)

        }
    }

    private fun initState() {
        val id = savedStateHandle[ProfileNavScreen.USER_ID_KEY] ?: ProfileNavScreen.SELF_PROFILE
        loadProfile(id)
    }

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
        currentJob = null
    }

    private fun onDispose() {
        _state.tryEmit(ProfileState())
        currentJob?.cancel()
    }

    private fun onDishClick(id: Int) {
        viewModelScope.launch { _action.emit(ProfileSideEffect.NavigateToDishDetailsScreen(id)) }
    }

    private fun onOrderClick(id: Int) {
        viewModelScope.launch { _action.emit(ProfileSideEffect.NavigateToOrderDetailsScreen(id)) }
    }


    private fun onDropdownMenuClick() {
        _state.tryEmit(
            _state.value.copy(
                isDropdownMenuExpanded = true
            )
        )
    }

    private fun onCloseDropdownMenu() {
        _state.tryEmit(
            _state.value.copy(
                isDropdownMenuExpanded = false
            )
        )
    }



    private fun loadProfile(id: String) {
        currentJob = viewModelScope.launch {
            try {
                val user = userRep.getUserByNumber("1")

                if (user != null) {
                    _state.emit(
                        _state.value.copy(
                            userPhone = user.phoneNumber,
                            userCity = user.city,
                            userCountry = user.country,
                            userFirstName = user.firstName,
                            userLastName = user.lastName
                        )
                    )
//                    launch {
//
//                    }
                } else {
                    _state.emit(_state.value.copy())
                }
            } catch (_: Exception) {
                _state.emit(_state.value.copy())
            }
        }
    }

//    private suspend fun loadRoutes(routesId: List<String>) {
//        try {
//            val routes = getRoutesByIdListUseCase(routesId)
//            _state.emit(_state.value.copy(userRoutes = routes.toPersistentList(), routesListState = ListState.CONTENT))
//        } catch (e: Exception) {
//            _state.emit(_state.value.copy(routesListState = ListState.ERROR))
//        }
//    }
//
//    private suspend fun loadPlaces(placesId: List<String>) {
//        try {
//            val places = getPlacesByIdListUseCase(placesId)
//            _state.emit(_state.value.copy(userPlaces = places.toPersistentList(), placesState = ListState.CONTENT))
//        } catch (e: Exception) {
//            _state.emit(_state.value.copy(placesState = ListState.ERROR))
//        }
//    }
}