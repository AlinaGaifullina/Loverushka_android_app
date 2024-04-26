package ru.itis.loverushka_app.ui.screens.dish_details

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.itis.loverushka_app.domain.model.Dish
import ru.itis.loverushka_app.domain.usecase.AddDishToFavouritesUseCase
import ru.itis.loverushka_app.domain.usecase.DeleteDishFromFavouritesUseCase
import ru.itis.loverushka_app.domain.usecase.GetDishByIdUseCase
import ru.itis.loverushka_app.domain.usecase.GetFavouritesByUserNumberUseCase
import ru.itis.loverushka_app.ui.navigation.graphs.HomeNavScreen
import javax.inject.Inject

@Immutable
data class DishDetailsState(
    val phoneNumber: String = "2",
    val dishId: Int = 0,
    val isFavouriteDish: Boolean = false,
    val favouritesId: List<Int> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = "",
    val data: Dish =
        Dish(
            0,
            "",
            "",
            "",
            "",
            0,
            ""
        )
)

sealed interface DishDetailsEvent {
    object OnLaunch : DishDetailsEvent
    object OnFavouriteChange : DishDetailsEvent
    object OnBackBtnClick : DishDetailsEvent
    data class OnBuyClick(val id: Int) : DishDetailsEvent

}

sealed interface DishDetailsSideEffect {
    object NavigateBack : DishDetailsSideEffect
}

@HiltViewModel
class DishDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFavouritesByUserNumberUseCase: GetFavouritesByUserNumberUseCase,
    private val addDishToFavouritesUseCase: AddDishToFavouritesUseCase,
    private val deleteDishFromFavouritesUseCase: DeleteDishFromFavouritesUseCase,
    private val getDishByIdUseCase: GetDishByIdUseCase
) : ViewModel(){

    private val dishId: Int = savedStateHandle[HomeNavScreen.DISH_ID_KEY] ?: 0


    private val _state: MutableStateFlow<DishDetailsState> =
        MutableStateFlow(DishDetailsState())
    val state: StateFlow<DishDetailsState> = _state

    private val _action = MutableSharedFlow<DishDetailsSideEffect?>()
    val action: SharedFlow<DishDetailsSideEffect?>
        get() = _action.asSharedFlow()

    fun event(dishDetailsEvent: DishDetailsEvent) {
        ViewModelProvider.NewInstanceFactory
        when (dishDetailsEvent) {
            DishDetailsEvent.OnLaunch -> onLaunch()
            DishDetailsEvent.OnFavouriteChange -> onFavouriteChange()
            DishDetailsEvent.OnBackBtnClick -> onBackBtnClick()
            is DishDetailsEvent.OnBuyClick -> onBuyClick(dishDetailsEvent.id)
            else -> {}
        }
    }

    private fun onLaunch() {
        viewModelScope.launch {
            val favouritesId = getFavouritesByUserNumberUseCase(_state.value.phoneNumber).map { it.favouritesId }
            val isFav = favouritesId.contains(_state.value.dishId)
            val dish = getDishByIdUseCase(dishId)
                _state.emit(
                    _state.value.copy(
                        dishId = dishId,
                        favouritesId = favouritesId,
                        isFavouriteDish = isFav,
                        data = dish
                    )
                )
        }
    }

    private fun onBackBtnClick(){
        viewModelScope.launch {
            _action.emit(DishDetailsSideEffect.NavigateBack)
        }
    }

    private fun onBuyClick(id: Int) {

    }

    private fun onFavouriteChange(){
        val isFav = _state.value.favouritesId.contains(_state.value.dishId)
        viewModelScope.launch {
            if(isFav){
                deleteDishFromFavouritesUseCase(_state.value.phoneNumber, state.value.dishId)
                val favouritesId = getFavouritesByUserNumberUseCase(_state.value.phoneNumber).map { it.favouritesId }
                _state.emit(
                    _state.value.copy(
                        favouritesId = favouritesId,
                        isFavouriteDish = false
                    )
                )
            } else {
                addDishToFavouritesUseCase(_state.value.phoneNumber, state.value.dishId)
                val favouritesId = getFavouritesByUserNumberUseCase(_state.value.phoneNumber).map { it.favouritesId }
                _state.emit(
                    _state.value.copy(
                        favouritesId = favouritesId,
                        isFavouriteDish = true
                    )
                )
            }
        }
    }
}