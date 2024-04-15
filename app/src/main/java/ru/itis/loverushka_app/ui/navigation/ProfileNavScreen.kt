package ru.itis.loverushka_app.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.itis.loverushka_app.ui.screens.profile.ProfileScreen

fun NavGraphBuilder.profileNavGraph(navController: NavHostController, isBottomBarVisible: MutableState<Boolean>) {
    navigation(
        route = BottomNavigationItem.Profile.graph,
        startDestination = ProfileNavScreen.Profile.route
    ) {

        composable(
            route = ProfileNavScreen.Profile.route,
        ) {
            isBottomBarVisible.value = true
            ProfileScreen(navController)
        }
        authNavGraph(navController = navController, isBottomBarVisible)
    }

}

sealed class ProfileNavScreen(val route: String) {


    object Profile : ProfileNavScreen(route = "profile")

    companion object {
        const val USER_ID_KEY = "userId"
        const val SELF_PROFILE = "self"
    }
}