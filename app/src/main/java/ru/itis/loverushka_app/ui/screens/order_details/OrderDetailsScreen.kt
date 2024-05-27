package ru.itis.loverushka_app.ui.screens.order_details

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
import ru.itis.loverushka_app.ui.navigation.graphs.ProfileNavScreen
import ru.itis.loverushka_app.ui.screens.home.HomeSideEffect

@Composable
fun OrderDetailsScreen(
    navController: NavController,
    viewModel:OrderDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(Unit) {
        eventHandler.invoke(OrderDetailsEvent.OnLaunch)
    }

    LaunchedEffect(action) {
        when (action) {
            is OrderDetailsSideEffect.NavigateToProfileScreen -> {
                navController.navigate(ProfileNavScreen.Profile.route)
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
                            OrderDetailsEvent.OnCloseClick(state.cartId)
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
            text = "Заказ оформлен",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 350.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))

    }
}