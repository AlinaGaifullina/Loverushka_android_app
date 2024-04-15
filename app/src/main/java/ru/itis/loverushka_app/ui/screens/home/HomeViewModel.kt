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
import ru.itis.loverushka_app.domain.model.Dish
import ru.itis.loverushka_app.domain.usecase.GetAllDishesUseCase
import javax.annotation.concurrent.Immutable
import javax.inject.Inject


@Immutable
data class HomeState(
    val searchValue: String = "",
    val searchByName: Boolean = true,
    val isLoading: Boolean = true,
    val dishes: List<Dish> = listOf()
)

sealed interface HomeEvent {
    object OnLaunch : HomeEvent
    object OnDispose : HomeEvent
    data class OnDishClick(val id: Int) : HomeEvent
    data class OnSearchValueChanged(val searchValue: String) : HomeEvent
}

sealed interface HomeSideEffect {
    data class NavigateToDishDetailsScreen(val id: Int) : HomeSideEffect
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllDishesUseCase: GetAllDishesUseCase
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
            is HomeEvent.OnSearchValueChanged -> onSearchValueChanged(homeEvent)
        }
    }

    private fun onLaunch() {
        viewModelScope.launch {
            allDishes = getAllDishesUseCase()
            _state.emit(
                _state.value.copy(
                    isLoading = false,
                    dishes = allDishes
                )
            )
        }
    }

    private fun onDispose() {
        allDishes = listOf()
    }


    private fun onDishClick(id: Int) {
        viewModelScope.launch {
            _action.emit(HomeSideEffect.NavigateToDishDetailsScreen(id))
        }
    }

    private fun onSearchValueChanged(event: HomeEvent.OnSearchValueChanged) {
        _state.tryEmit(_state.value.copy(searchValue = event.searchValue))
    }


//    private fun onFilterBtnClick() {
//        viewModelScope.launch {
//            _action.emit(HomeSideEffect.NavigateToSearchScreen)
//        }
//    }
}