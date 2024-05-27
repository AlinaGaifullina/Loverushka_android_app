package ru.itis.loverushka_app.ui.navigation.graphs

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.itis.loverushka_app.ui.navigation.BottomNavigationItem
import ru.itis.loverushka_app.ui.screens.cart.CartScreen
import ru.itis.loverushka_app.ui.screens.dish_details.DishDetailsScreen
import ru.itis.loverushka_app.ui.screens.make_order.MakeOrderScreen
import ru.itis.loverushka_app.ui.screens.order_details.OrderDetailsScreen

fun NavGraphBuilder.cartNavGraph(navController: NavHostController, isBottomBarVisible: MutableState<Boolean>) {
    navigation(
        route = BottomNavigationItem.Cart.graph,
        startDestination = CartNavScreen.Cart.route
    ) {
        composable(
            route = CartNavScreen.Cart.route,
        ) {
            isBottomBarVisible.value = true
            CartScreen(
                navController = navController,
            )
        }
        composable(
            route = CartNavScreen.MakeOrder.route,
            arguments = listOf(
                navArgument("cartId") {
                    type = NavType.IntType
                }
            )
        ) {
            isBottomBarVisible.value = false
            MakeOrderScreen(navController = navController)
        }

        composable(
            route = CartNavScreen.OrderDetails.route,
            arguments = listOf(
                navArgument("orderId") {
                    type = NavType.IntType
                }
            )
        ) {
            isBottomBarVisible.value = false
            OrderDetailsScreen(navController = navController)
        }
    }
}

sealed class CartNavScreen(val route: String) {

    object Cart : CartNavScreen(route = "cart_screen")

    object MakeOrder : CartNavScreen(route = "make_order_screen/{$CART_ID_KEY}") {
        fun passCartId(cartId: Int) = "make_order_screen/$cartId"
    }
    object OrderDetails : CartNavScreen(route = "order_details_screen/{$ORDER_ID_KEY}") {
        fun passOrderId(orderId: Int) = "order_details_screen/$orderId"
    }

    companion object {
        const val ORDER_ID_KEY = "orderId"
        const val CART_ID_KEY = "cartId"
    }
}