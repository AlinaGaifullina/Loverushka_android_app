package ru.itis.loverushka_app.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.itis.loverushka_app.ui.screens.login.LoginScreen
import ru.itis.loverushka_app.ui.screens.register.RegisterScreen


fun NavGraphBuilder.authNavGraph(navController: NavHostController, isBottomBarVisible: MutableState<Boolean>) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.SignIn.route
    ) {
        composable(route = AuthScreen.SignIn.route) {
            isBottomBarVisible.value = false
            LoginScreen(navController)
        }
        composable(route = AuthScreen.SignUp.route) {
            isBottomBarVisible.value = false
            RegisterScreen(navController)
        }
        composable(route = AuthScreen.Forgot.route) {
            //TODO
        }
    }

}

sealed class AuthScreen(val route: String) {
    object SignIn : AuthScreen(route = "sign_in")
    object SignUp : AuthScreen(route = "sign_up")
    object Forgot : AuthScreen(route = "forgot") //TODO
}