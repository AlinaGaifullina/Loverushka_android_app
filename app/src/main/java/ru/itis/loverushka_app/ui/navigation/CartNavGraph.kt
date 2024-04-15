package ru.itis.loverushka_app.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.cartNavGraph(navController: NavHostController, isBottomBarVisible: MutableState<Boolean>) {
    navigation(
        route = BottomNavigationItem.Cart.graph,
        startDestination = CartNavScreen.Cart.route
    ) {
        composable(
            route = HomeNavScreen.Home.route,
        ) {
            isBottomBarVisible.value = true
//            CartScreen(
//                navController = navController,
//            )
        }
    }
}

sealed class CartNavScreen(val route: String) {

    object Cart : HomeNavScreen(route = "cart_screen")
}