package ru.itis.loverushka_app.ui.screens.order_details

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
import ru.itis.loverushka_app.ui.navigation.graphs.CartNavScreen
import javax.inject.Inject

@Immutable
data class OrderDetailsState(
    val phoneNumber: String = "2",
    val cartId: Int = 0,
    val isLoading: Boolean = true,
    val dishes: List<Dish> = emptyList(),
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

sealed interface OrderDetailsEvent {
    object OnLaunch : OrderDetailsEvent
    data class OnCloseClick(val cartId: Int) : OrderDetailsEvent
}

sealed interface OrderDetailsSideEffect {
    data class NavigateToProfileScreen(val cartId: Int) : OrderDetailsSideEffect
}

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFavouritesByUserNumberUseCase: GetFavouritesByUserNumberUseCase,
    private val getCartByPhoneNumberUseCase: GetCartByPhoneNumberUseCase,
    private val deleteDishFromCartUseCase: DeleteDishFromCartUseCase,
    private val changeNumberOfDishInCartUseCase: ChangeNumberOfDishInCartUseCase,
    private val getDishByIdUseCase: GetDishByIdUseCase,
) : ViewModel(){

    private val cartId: Int = savedStateHandle[CartNavScreen.CART_ID_KEY] ?: 0


    private val _state: MutableStateFlow<OrderDetailsState> =
        MutableStateFlow(OrderDetailsState())
    val state: StateFlow<OrderDetailsState> = _state

    private val _action = MutableSharedFlow<OrderDetailsSideEffect?>()
    val action: SharedFlow<OrderDetailsSideEffect?>
        get() = _action.asSharedFlow()

    fun event(orderDetailsEvent: OrderDetailsEvent) {
        ViewModelProvider.NewInstanceFactory
        when (orderDetailsEvent) {
            OrderDetailsEvent.OnLaunch -> onLaunch()
            is OrderDetailsEvent.OnCloseClick -> onCloseClick(orderDetailsEvent.cartId)
            else -> {}
        }
    }

    private fun onLaunch() {
        viewModelScope.launch {
            val cart = getCartByPhoneNumberUseCase(_state.value.phoneNumber)
            _state.emit(
                _state.value.copy(
                    data = cart,
                    cartId = cartId
                )
            )
            val dishes = mutableListOf<Dish>()
            for(i in _state.value.data.dishes){
                if(_state.value.data.checkedDishes.contains(i)) {
                    dishes.add(getDishByIdUseCase(i))
                }
            }
            _state.emit(
                _state.value.copy(
                    dishes = dishes
                )
            )
        }
    }
    private fun onCloseClick(cartId: Int) {
        viewModelScope.launch {
            _action.emit(OrderDetailsSideEffect.NavigateToProfileScreen(cartId))
        }
    }
}