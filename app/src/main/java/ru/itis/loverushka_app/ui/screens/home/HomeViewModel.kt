package ru.itis.loverushka_app.ui.screens.home

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
import ru.itis.loverushka_app.data.entity.FavouritesEntity
import ru.itis.loverushka_app.domain.model.Cart
import ru.itis.loverushka_app.domain.model.Dish
import ru.itis.loverushka_app.domain.model.Favourites
import ru.itis.loverushka_app.domain.repository.DishRepository
import ru.itis.loverushka_app.domain.usecase.AddDishToCartUseCase
import ru.itis.loverushka_app.domain.usecase.AddDishToFavouritesUseCase
import ru.itis.loverushka_app.domain.usecase.ChangeCheckedDishesInCartUseCase
import ru.itis.loverushka_app.domain.usecase.ChangeNumberOfDishInCartUseCase
import ru.itis.loverushka_app.domain.usecase.DeleteDishFromCartUseCase
import ru.itis.loverushka_app.domain.usecase.DeleteDishFromFavouritesUseCase
import ru.itis.loverushka_app.domain.usecase.GetAllDishesUseCase
import ru.itis.loverushka_app.domain.usecase.GetCartByPhoneNumberUseCase
import ru.itis.loverushka_app.domain.usecase.GetFavouritesByUserNumberUseCase
import ru.itis.loverushka_app.ui.screens.cart.CartEvent
import javax.annotation.concurrent.Immutable
import javax.inject.Inject


@Immutable
data class HomeState(
    val phoneNumber: String = "2",
    val searchValue: String = "",
    val searchByName: Boolean = true,
    val isLoading: Boolean = true,
    val dishes: List<Dish> = listOf(),
    val favouritesId: List<Int> = listOf(),
    val cart: Cart =
        Cart(
            0,
            "",
            "",
            listOf(),
            0,
            listOf(),
            listOf()
        )
)

sealed interface HomeEvent {
    object OnLaunch : HomeEvent
    object OnDispose : HomeEvent
    data class OnDishClick(val id: Int) : HomeEvent
    data class OnLikeClick(val id: Int) : HomeEvent
    data class OnBuyClick(val id: Int) : HomeEvent
    data class OnSearchValueChanged(val searchValue: String) : HomeEvent
    data class OnPlusMinusClick(val dishId: Int, val sign: Boolean) : HomeEvent
}

sealed interface HomeSideEffect {
    data class NavigateToDishDetailsScreen(val id: Int) : HomeSideEffect
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllDishesUseCase: GetAllDishesUseCase,
    private val getFavouritesByUserNumberUseCase: GetFavouritesByUserNumberUseCase,
    private val addDishToFavouritesUseCase: AddDishToFavouritesUseCase,
    private val deleteDishFromFavouritesUseCase: DeleteDishFromFavouritesUseCase,
    private val getCartByPhoneNumberUseCase: GetCartByPhoneNumberUseCase,
    private val addDishToCartUseCase: AddDishToCartUseCase,
    private val deleteDishFromCartUseCase: DeleteDishFromCartUseCase,
    private val changeNumberOfDishInCartUseCase: ChangeNumberOfDishInCartUseCase,
    private val changeCheckedDishesInCartUseCase: ChangeCheckedDishesInCartUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _action = MutableSharedFlow<HomeSideEffect?>()
    val action: SharedFlow<HomeSideEffect?>
        get() = _action.asSharedFlow()

    private var allDishes: List<Dish> = listOf()

    fun event(homeEvent: HomeEvent) {
        ViewModelProvider.NewInstanceFactory
        when (homeEvent) {
            HomeEvent.OnLaunch -> onLaunch()
            HomeEvent.OnDispose -> onDispose()
            is HomeEvent.OnDishClick -> onDishClick(homeEvent.id)
            is HomeEvent.OnLikeClick -> onLikeClick(homeEvent.id)
            is HomeEvent.OnBuyClick -> onBuyClick(homeEvent.id)
            is HomeEvent.OnSearchValueChanged -> onSearchValueChanged(homeEvent)
            is HomeEvent.OnPlusMinusClick -> onPlusMinusClick(homeEvent.dishId, homeEvent.sign)
        }
    }

    private fun onLaunch() {
        viewModelScope.launch {
            allDishes = getAllDishesUseCase()
            val favouritesId = getFavouritesByUserNumberUseCase(_state.value.phoneNumber).map { it.favouritesId }
            val cart = getCartByPhoneNumberUseCase(_state.value.phoneNumber)
            _state.emit(
                _state.value.copy(
                    isLoading = false,
                    dishes = allDishes,
                    favouritesId = favouritesId,
                    cart = cart
                )
            )
        }
    }

    private fun onDispose() {
        allDishes = listOf()
    }

    private fun onBuyClick(dishId: Int) {
        viewModelScope.launch {
            val newDishesId = _state.value.cart.dishes.toMutableList()
            val newNumberOfDishes = _state.value.cart.numberOfDishes.toMutableList()
            val newCheckedDishes = _state.value.cart.checkedDishes.toMutableList()

            newDishesId.add(dishId)
            newNumberOfDishes.add(1)
            newCheckedDishes.add(dishId)
            addDishToCartUseCase(_state.value.phoneNumber, newDishesId, newNumberOfDishes)
            changeCheckedDishesInCartUseCase(_state.value.phoneNumber, newCheckedDishes)
            val cart = getCartByPhoneNumberUseCase(_state.value.phoneNumber)
            _state.emit(
                _state.value.copy(
                    cart = cart
                )
            )
        }
    }

    private fun onPlusMinusClick(dishId: Int, sign: Boolean) {
        viewModelScope.launch {
            val newDishesId = _state.value.cart.dishes.toMutableList()
            val newNumberOfDishes = _state.value.cart.numberOfDishes.toMutableList()
            val newCheckedDishes = _state.value.cart.checkedDishes.toMutableList()
            val indexOfDish = _state.value.cart.dishes.indexOf(dishId)
            if (sign){ //плюс
                newNumberOfDishes[indexOfDish]++
                changeNumberOfDishInCartUseCase(_state.value.phoneNumber, newNumberOfDishes)

            } else { //минус
                if (newNumberOfDishes[indexOfDish] <= 1) {
                    newDishesId.remove(dishId)
                    newNumberOfDishes.removeAt(indexOfDish)
                    newCheckedDishes.remove(dishId)
                    deleteDishFromCartUseCase(_state.value.phoneNumber, newDishesId, newNumberOfDishes)
                    changeCheckedDishesInCartUseCase(_state.value.phoneNumber, newCheckedDishes)
                } else {
                    newNumberOfDishes[indexOfDish]--
                    changeNumberOfDishInCartUseCase(_state.value.phoneNumber, newNumberOfDishes)
                }
            }
            val cart = getCartByPhoneNumberUseCase(_state.value.phoneNumber)
            _state.emit(
                _state.value.copy(
                    cart = cart
                )
            )
        }
    }

    private fun onDishClick(id: Int) {
        viewModelScope.launch {
            _action.emit(HomeSideEffect.NavigateToDishDetailsScreen(id))
        }
    }

    private fun onLikeClick(id: Int) {
        viewModelScope.launch {
            val isFav = _state.value.favouritesId.contains(id)
            if(isFav){
                deleteDishFromFavouritesUseCase(_state.value.phoneNumber, id)
                val favouritesId = getFavouritesByUserNumberUseCase(_state.value.phoneNumber).map { it.favouritesId }
                _state.emit(
                    _state.value.copy(
                        favouritesId = favouritesId
                    )
                )
            } else {
                addDishToFavouritesUseCase(_state.value.phoneNumber, id)
                val favouritesId = getFavouritesByUserNumberUseCase(_state.value.phoneNumber).map { it.favouritesId }
                _state.emit(
                    _state.value.copy(
                        favouritesId = favouritesId
                    )
                )
            }
        }
    }

    private fun onSearchValueChanged(event: HomeEvent.OnSearchValueChanged) {
        _state.tryEmit(_state.value.copy(searchValue = event.searchValue))
    }
}