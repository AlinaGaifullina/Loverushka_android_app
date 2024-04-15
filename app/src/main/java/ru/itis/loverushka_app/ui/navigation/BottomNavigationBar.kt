package ru.itis.loverushka_app.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationBar(navController: NavController, isBottomBarVisible: MutableState<Boolean>) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Cart,
        BottomNavigationItem.Profile
    )

    AnimatedVisibility(
        visible = isBottomBarVisible.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.White)
            )
            NavigationBar(
                modifier = Modifier
                    .height(64.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                items.forEach { item ->
                    NavigationBarItem(
//                        modifier = Modifier.offset(
//                            when (item.title) {
//                                BottomNavigationItem.Settins.title -> 20.dp
//                                BottomNavigationItem.Map.title -> (-20).dp
//                                else -> 0.dp
//                            }
//                        ),
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                                modifier = Modifier.size(27.dp)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                        ),
                        alwaysShowLabel = false,
                        selected = currentDestination?.hierarchy?.any { it.route == item.graph } == true,
                        onClick = {
                            if (currentDestination?.hierarchy?.any{it.route == item.graph} == true) return@NavigationBarItem
                            navController.navigate(item.graph) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetBottomNavigationBar() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    val isBottomBarVisible = rememberSaveable { (mutableStateOf(true)) }

    // BottomBar content:

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                AnimatedVisibility(
                    visible = isBottomBarVisible.value,
                    enter = slideInVertically(initialOffsetY = { 270 }),
                    exit = slideOutVertically(targetOffsetY = { 270 }),
                ) {
                    BottomNavigationBar(navController = navController, isBottomBarVisible = isBottomBarVisible)
                }
            }
        },
        content = {
            RootNavGraph(navController = navController, isBottomBarVisible)
        }
    )
}