package ru.itis.loverushka_app.ui.screens.make_order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.itis.loverushka_app.R
import ru.itis.loverushka_app.ui.components.AuthButton
import ru.itis.loverushka_app.ui.navigation.graphs.CartNavScreen
import ru.itis.loverushka_app.ui.navigation.graphs.HomeNavScreen
import ru.itis.loverushka_app.ui.screens.home.HomeSideEffect

@Composable
fun MakeOrderScreen(
    navController: NavController,
    viewModel: MakeOrderViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(Unit) {
        eventHandler.invoke(MakeOrderEvent.OnLaunch)
    }

    LaunchedEffect(action) {
        when (action) {
            is MakeOrderSideEffect.NavigateToDishDetailsScreen -> {
                navController.navigate(HomeNavScreen.Dish.passDishId((action as HomeSideEffect.NavigateToDishDetailsScreen).id))
            }
            is MakeOrderSideEffect.NavigateToOrderDetailsScreen -> {
                navController.navigate(CartNavScreen.OrderDetails.passOrderId((action as MakeOrderSideEffect.NavigateToOrderDetailsScreen).cartId))
            }
            is MakeOrderSideEffect.NavigateToCartScreen -> {
                navController.navigate(CartNavScreen.Cart.route)
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ){
            Icon(
                painterResource(R.drawable.ic_close),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp)
                    .clickable {
                        eventHandler.invoke(
                            MakeOrderEvent.OnCloseOrderClick(state.cartId)
                        )
                    }
                    .size(32.dp)
                    .align(Alignment.TopEnd),
                contentDescription = "icon",
                tint = MaterialTheme.colorScheme.surface
            )
            Text(
                text = "Оформление заказа",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .align(Alignment.TopCenter),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Text(
            text = "Доставка на адрес: ${state.data.address}",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.tertiary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Text(
                    text = "Заказ приедет в 15:10",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.Bottom
                ) {
                    state.dishes.forEach {dish ->
                        Column(
                            modifier = Modifier
                                .width(160.dp)
                                .padding(horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${dish.dishName}",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp),
                                style = MaterialTheme.typography.titleSmall.copy(lineHeight = 15.sp),
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            Box(
                                modifier = Modifier
                                    .height(105.dp)
                                    .width(145.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .height(100.dp)
                                        .width(140.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .align(Alignment.Center),
                                    //.size(width = 180.dp, height = 140.dp),
                                    model = dish.photo,
                                    contentDescription = "photo",
                                    clipToBounds = true,
                                    contentScale = ContentScale.FillBounds,
                                )
                            }
                            Text(
                                text = "${state.data.numberOfDishes[state.data.dishes.indexOf(dish.dishId)]} * ${dish.price} р.",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        }
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.tertiary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Text(
                    text = "Способ оплаты",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.Bottom
                ) {
                    

                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.tertiary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Text(
                    text = "Ваш заказ",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
                Column {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Блюда (${
                                state.dishes
                                    .filter { state.data.checkedDishes.contains(it.dishId) }
                                    .sumOf { state.data.numberOfDishes[state.data.dishes.indexOf(it.dishId)] }
                            })",
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                        Text(
                            text = "${
                                state.dishes
                                    .filter { state.data.checkedDishes.contains(it.dishId) }
                                    .sumOf { dish -> (dish.price * state.data.numberOfDishes[state.data.dishes.indexOf(dish.dishId)]) }
                            } р.",
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Доставка",
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                        Text(
                            text = "95 р.",
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Итого",
                            modifier = Modifier,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                        Text(
                            text = "${
                                state.dishes
                                    .filter { state.data.checkedDishes.contains(it.dishId) }
                                    .sumOf { dish -> (dish.price * state.data.numberOfDishes[state.data.dishes.indexOf(dish.dishId)]) } + 95
                            } р.",
                            modifier = Modifier,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }
            }
        }
        AuthButton(
            onClick = { eventHandler.invoke(MakeOrderEvent.OnBuyClick(state.data.cartId)) },
            text = "Оформить",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp, vertical = 16.dp)
                .height(60.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}