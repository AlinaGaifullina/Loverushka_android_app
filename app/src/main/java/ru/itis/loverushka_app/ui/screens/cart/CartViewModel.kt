package ru.itis.loverushka_app.ui.screens.cart

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
import ru.itis.loverushka_app.domain.model.Cart
import ru.itis.loverushka_app.domain.model.Dish
import ru.itis.loverushka_app.domain.usecase.AddDishToFavouritesUseCase
import ru.itis.loverushka_app.domain.usecase.ChangeCheckedDishesInCartUseCase
import ru.itis.loverushka_app.domain.usecase.ChangeNumberOfDishInCartUseCase
import ru.itis.loverushka_app.domain.usecase.DeleteDishFromCartUseCase
import ru.itis.loverushka_app.domain.usecase.DeleteDishFromFavouritesUseCase
import ru.itis.loverushka_app.domain.usecase.GetCartByPhoneNumberUseCase
import ru.itis.loverushka_app.domain.usecase.GetDishByIdUseCase
import ru.itis.loverushka_app.domain.usecase.GetFavouritesByUserNumberUseCase
import javax.inject.Inject

@Immutable
data class CartState(
    val phoneNumber: String = "2",
    val cartId: Int = 0,
    val favouritesId: List<Int> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = "",
    val dishes: List<Dish> = listOf(),
    val data: Cart =
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

sealed interface CartEvent {
    object OnLaunch : CartEvent
    data class OnDishClick(val dishId: Int) : CartEvent
    data class OnDeleteDishClick(val dishId: Int) : CartEvent
    data class OnMakeOrderClick(val cartId: Int) : CartEvent
    data class OnCheckboxClick(val dishId: Int, val isChecked: Boolean) : CartEvent

    data class OnPlusMinusClick(val dishId: Int, val sign: Boolean) : CartEvent

    data class OnLikeClick(val dishId: Int) : CartEvent
}

sealed interface CartSideEffect {
    data class NavigateToDishDetailsScreen(val dishId: Int) : CartSideEffect
    data class NavigateToMakeOrderScreen(val cartId: Int) : CartSideEffect
}

@HiltViewModel
class CartViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFavouritesByUserNumberUseCase: GetFavouritesByUserNumberUseCase,
    private val getCartByPhoneNumberUseCase: GetCartByPhoneNumberUseCase,
    private val deleteDishFromCartUseCase: DeleteDishFromCartUseCase,
    private val changeNumberOfDishInCartUseCase: ChangeNumberOfDishInCartUseCase,
    private val addDishToFavouritesUseCase: AddDishToFavouritesUseCase,
    private val deleteDishFromFavouritesUseCase: DeleteDishFromFavouritesUseCase,
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val changeCheckedDishesInCartUseCase: ChangeCheckedDishesInCartUseCase
) : ViewModel(){

    //private val dishId: Int = savedStateHandle[HomeNavScreen.DISH_ID_KEY] ?: 0


    private val _state: MutableStateFlow<CartState> =
        MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state

    private val _action = MutableSharedFlow<CartSideEffect?>()
    val action: SharedFlow<CartSideEffect?>
        get() = _action.asSharedFlow()

    fun event(cartEvent: CartEvent) {
        ViewModelProvider.NewInstanceFactory
        when (cartEvent) {
            CartEvent.OnLaunch -> onLaunch()
            is CartEvent.OnDishClick -> onDishClick(cartEvent.dishId)
            is CartEvent.OnDeleteDishClick -> onDeleteDishClick(cartEvent.dishId)
            is CartEvent.OnMakeOrderClick -> onMakeOrderClick(cartEvent.cartId)
            is CartEvent.OnPlusMinusClick -> onPlusMinusClick(cartEvent.dishId, cartEvent.sign)
            is CartEvent.OnLikeClick -> onLikeClick(cartEvent.dishId)
            is CartEvent.OnCheckboxClick -> onCheckboxClick(cartEvent.dishId, cartEvent.isChecked)
            else -> {}
        }
    }

    private fun onLaunch() {
        viewModelScope.launch {
            val favouritesId = getFavouritesByUserNumberUseCase(_state.value.phoneNumber).map { it.favouritesId }
            val cart = getCartByPhoneNumberUseCase(_state.value.phoneNumber)
            _state.emit(
                _state.value.copy(
                    favouritesId = favouritesId,
                    data = cart
                )
            )
            val dishes = mutableListOf<Dish>()
            for(i in _state.value.data.dishes){
                dishes.add(getDishByIdUseCase(i))
            }
            _state.emit(
                _state.value.copy(
                    dishes = dishes
                )
            )
        }
    }

    private fun onDishClick(dishId: Int) {
        viewModelScope.launch {
            _action.emit(CartSideEffect.NavigateToDishDetailsScreen(dishId))
        }
    }

    private fun onCheckboxClick(dishId: Int, isChecked: Boolean) {
        viewModelScope.launch {
            val newCheckedDishes = _state.value.data.checkedDishes.toMutableList()
            if(isChecked){
                newCheckedDishes.remove(dishId)
                changeCheckedDishesInCartUseCase(_state.value.phoneNumber, newCheckedDishes)
            } else {
                newCheckedDishes.add(dishId)
                changeCheckedDishesInCartUseCase(_state.value.phoneNumber, newCheckedDishes)
            }
            val cart = getCartByPhoneNumberUseCase(_state.value.phoneNumber)
            _state.emit(
                _state.value.copy(
                    data = cart
                )
            )
        }
    }

    private fun onPlusMinusClick(dishId: Int, sign: Boolean) {
        viewModelScope.launch {
            val newDishesId = _state.value.data.dishes.toMutableList()
            val newNumberOfDishes = _state.value.data.numberOfDishes.toMutableList()
            val newCheckedDishes = _state.value.data.checkedDishes.toMutableList()
            val indexOfDish = _state.value.data.dishes.indexOf(dishId)
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
                    data = cart
                )
            )
            val dishes = mutableListOf<Dish>()
            for(i in _state.value.data.dishes){
                dishes.add(getDishByIdUseCase(i))
            }
            _state.emit(
                _state.value.copy(
                    dishes = dishes
                )
            )
        }
    }

    private fun onMakeOrderClick(cartId: Int) {
        viewModelScope.launch {
            _action.emit(CartSideEffect.NavigateToMakeOrderScreen(cartId))
        }
    }

    private fun onDeleteDishClick(dishId: Int) {
        viewModelScope.launch {
            val newDishesId = _state.value.data.dishes.toMutableList()
            val newNumberOfDishes = _state.value.data.numberOfDishes.toMutableList()
            val newCheckedDishes = _state.value.data.checkedDishes.toMutableList()
            val indexOfDish = _state.value.data.dishes.indexOf(dishId)
            newDishesId.remove(dishId)
            newNumberOfDishes.removeAt(indexOfDish)
            newCheckedDishes.remove(dishId)
            deleteDishFromCartUseCase(_state.value.phoneNumber, newDishesId, newNumberOfDishes)
            changeCheckedDishesInCartUseCase(_state.value.phoneNumber, newCheckedDishes)
            val cart = getCartByPhoneNumberUseCase(_state.value.phoneNumber)
            _state.emit(
                _state.value.copy(
                    data = cart,
                )
            )
            val dishes = mutableListOf<Dish>()
            for(i in _state.value.data.dishes){
                dishes.add(getDishByIdUseCase(i))
            }
            _state.emit(
                _state.value.copy(
                    dishes = dishes
                )
            )
        }
    }

    private fun onLikeClick(dishId: Int){
        val isFav = _state.value.favouritesId.contains(dishId)
        viewModelScope.launch {
            if(isFav){
                deleteDishFromFavouritesUseCase(_state.value.phoneNumber, dishId)
                val favouritesId = getFavouritesByUserNumberUseCase(_state.value.phoneNumber).map { it.favouritesId }
                _state.emit(
                    _state.value.copy(
                        favouritesId = favouritesId,
                    )
                )
            } else {
                addDishToFavouritesUseCase(_state.value.phoneNumber, dishId)
                val favouritesId = getFavouritesByUserNumberUseCase(_state.value.phoneNumber).map { it.favouritesId }
                _state.emit(
                    _state.value.copy(
                        favouritesId = favouritesId,
                    )
                )
            }
        }
    }
}