package ru.itis.loverushka_app.ui.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import ru.itis.loverushka_app.ui.navigation.graphs.authNavGraph
import ru.itis.loverushka_app.ui.navigation.graphs.cartNavGraph
import ru.itis.loverushka_app.ui.navigation.graphs.homeNavGraph
import ru.itis.loverushka_app.ui.navigation.graphs.profileNavGraph


@Composable
fun RootNavGraph(navController: NavHostController, isBottomBarVisible: MutableState<Boolean>) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.BOTTOM,
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
        authNavGraph(navController = navController, isBottomBarVisible)
        bottomNavGraph(navController = navController, isBottomBarVisible)
    }
}



fun NavGraphBuilder.bottomNavGraph(navController: NavHostController, isBottomBarVisible: MutableState<Boolean>) {
    navigation(
        startDestination = BottomNavigationItem.Home.graph,
        route = Graph.BOTTOM
    ) {

        //Графы к каждой вкладке Bottom Navigation:
        homeNavGraph(navController = navController, isBottomBarVisible)
        cartNavGraph(navController = navController, isBottomBarVisible)
        profileNavGraph(navController = navController, isBottomBarVisible)


        //authNavGraph(navController = navController)
    }

}

object Graph {
    const val AUTHENTICATION = "auth_graph"
    const val BOTTOM = "bottom_graph"
    const val ROOT = "root_graph"
}