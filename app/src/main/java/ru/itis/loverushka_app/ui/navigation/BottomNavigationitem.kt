package ru.itis.loverushka_app.ui.navigation

import ru.itis.loverushka_app.R

sealed class BottomNavigationItem(var graph: String, var icon: Int, var title: String) {

    object Home : BottomNavigationItem(
        "home_graph",
        R.drawable.ic_home,
        "Home"
    )

    object Cart : BottomNavigationItem(
        "cart_graph",
        R.drawable.ic_cart,
        "Cart"
    )

    object Profile : BottomNavigationItem(
        "profile_graph",
        R.drawable.ic_user,
        "Profile"
    )
}