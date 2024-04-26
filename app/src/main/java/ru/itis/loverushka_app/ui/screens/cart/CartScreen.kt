package ru.itis.loverushka_app.ui.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.itis.loverushka_app.R
import ru.itis.loverushka_app.ui.components.AuthButton
import ru.itis.loverushka_app.ui.components.DishInCartCard
import ru.itis.loverushka_app.ui.navigation.graphs.CartNavScreen
import ru.itis.loverushka_app.ui.navigation.graphs.HomeNavScreen
import ru.itis.loverushka_app.ui.screens.home.HomeSideEffect

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(Unit) {
        eventHandler.invoke(CartEvent.OnLaunch)
    }

    LaunchedEffect(action) {
        when (action) {
            is CartSideEffect.NavigateToDishDetailsScreen -> {
                navController.navigate(HomeNavScreen.Dish.passDishId((action as CartSideEffect.NavigateToDishDetailsScreen).dishId))
            }
            is CartSideEffect.NavigateToMakeOrderScreen -> {
                navController.navigate(CartNavScreen.MakeOrder.passCartId((action as CartSideEffect.NavigateToMakeOrderScreen).cartId))
            }
            null -> Unit
        }
    }
    val listState = rememberScrollState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(listState)
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 16.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = "Корзина",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Text(
            text = "Доставка на адрес: ${state.data.address}",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 3.dp,
            color = MaterialTheme.colorScheme.secondary
        )

        for(dish in state.dishes) {
            DishInCartCard(
                modifier = Modifier,
                dish = dish,
                numberOfDishes = state.data.numberOfDishes[state.data.dishes.indexOf(dish.dishId)],
                isCheckboxOn = state.data.checkedDishes.contains(dish.dishId),
                onCardClick = {
                    eventHandler.invoke(
                        CartEvent.OnDishClick(dish.dishId)
                    )
                },
                onCheckboxClick = {
                    eventHandler.invoke(
                        CartEvent.OnCheckboxClick(dish.dishId, state.data.checkedDishes.contains(dish.dishId))
                    )
                },
                onBinClick = {
                    eventHandler.invoke(
                        CartEvent.OnDeleteDishClick(dish.dishId)
                    )
                },
                onPlusMinusClick = { sign ->
                    eventHandler.invoke(
                        CartEvent.OnPlusMinusClick(dish.dishId, sign)
                    )
                }
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 64.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "${
                    state.dishes
                        .filter { state.data.checkedDishes.contains(it.dishId) }
                        .sumOf { dish -> (dish.dishPrice * state.data.numberOfDishes[state.data.dishes.indexOf(dish.dishId)]) }
                } р.",
                modifier = Modifier,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            AuthButton(
                onClick = {
                    eventHandler.invoke(
                        CartEvent.OnMakeOrderClick(state.cartId)
                    )
                },
                text = "К оформлению"
            )
        }
    }
}