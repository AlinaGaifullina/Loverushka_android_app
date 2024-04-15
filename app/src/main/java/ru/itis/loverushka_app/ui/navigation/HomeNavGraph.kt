package ru.itis.loverushka_app.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.itis.loverushka_app.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController, isBottomBarVisible: MutableState<Boolean>) {
    navigation(
        route = BottomNavigationItem.Home.graph,
        startDestination = HomeNavScreen.Home.route
    ) {
        composable(
            route = HomeNavScreen.Home.route
        ) {
            isBottomBarVisible.value = true
            HomeScreen(
                navController = navController,
            )
        }

        composable(
            route = HomeNavScreen.Dish.route,
            arguments = listOf(
                navArgument("dishId") {
                    type = NavType.IntType
                }
            )
        ) {
            isBottomBarVisible.value = true
//            HomeScreen(
//                navController = navController,
//            )
        }

        composable(
            route = HomeNavScreen.Order.route,
            arguments = listOf(
                navArgument("orderId") {
                    type = NavType.IntType
                }
            )
        ) {
            isBottomBarVisible.value = true
//            HomeScreen(
//                navController = navController,
//            )
        }
    }
}

sealed class HomeNavScreen(val route: String) {

    object Home : HomeNavScreen(route = "home_screen") {
//        fun passValues(searchValue: String, searchType: Boolean, searchTags: String) =
//            "home_screen/$searchValue/$searchType/$searchTags"
    }

    object Search : HomeNavScreen(route = "home_search_screen/{$SEARCH_VALUE_KEY}/{$SEARCH_SCREEN_KEY}") {
        fun passSearchValue(searchValue: String, screenName: String) = "home_search_screen/$searchValue/$screenName"
    }

    object Dish : HomeNavScreen(route = "dish/{$DISH_ID_KEY}") {
        fun passDishId(id: Int) = "route/$id"
    }
    object Order : HomeNavScreen(route = "dish/{$ORDER_ID_KEY}") {
        fun passOrderId(id: Int) = "route/$id"
    }

    companion object {
        const val SEARCH_VALUE_KEY = "searchValue"
        const val SEARCH_BY_NAME_KEY = "searchType"
        const val ORDER_ID_KEY = "orderId"
        const val SEARCH_SCREEN_KEY = "screenName"
        const val DISH_ID_KEY = "dishId"
    }
}