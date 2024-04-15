package ru.itis.loverushka_app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.itis.loverushka_app.R
import ru.itis.loverushka_app.ui.components.AnimatedBackgroundImage
import ru.itis.loverushka_app.ui.components.LocationText
import ru.itis.loverushka_app.ui.components.SwitchButton
import ru.itis.loverushka_app.ui.navigation.HomeNavScreen

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(Unit) {
        eventHandler.invoke(ProfileEvent.OnLaunch)
    }
    DisposableEffect(Unit) {
        onDispose {
            eventHandler.invoke(ProfileEvent.OnDispose)
        }
    }

    LaunchedEffect(action) {
        when (action) {
            null -> Unit

            is ProfileSideEffect.NavigateToDishDetailsScreen -> {
                navController.navigate(HomeNavScreen.Dish.passDishId((action as ProfileSideEffect.NavigateToDishDetailsScreen).id))
            }

            is ProfileSideEffect.NavigateToOrderDetailsScreen -> {
                navController.navigate(HomeNavScreen.Order.passOrderId((action as ProfileSideEffect.NavigateToOrderDetailsScreen).id))
            }
        }
    }

    val columnState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
            .padding(bottom = 64.dp),
        verticalArrangement = Arrangement.spacedBy((-32).dp),
        state = columnState
    )
    {
        item {
            AnimatedBackgroundImage(columnState = columnState)
        }
        Publications(state, eventHandler)
    }
}

private fun LazyListScope.Publications(state: ProfileState, eventHandler: (ProfileEvent) -> Unit) {
    item {
        ProfileMainContent(state, eventHandler)
    }


    item {
        Text(
            text = "Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать Люблю кушать",
            modifier = Modifier.padding(vertical = 36.dp, horizontal = 20.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }

    item {
        Text(
            text = "Заказы",
            modifier = Modifier.padding(vertical = 36.dp, horizontal = 20.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }

    item {
        Spacer(modifier = Modifier.height(80.dp))
    }
}


private fun LazyListScope.progressBar() {
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
        }
    }
}


@Composable
fun ProfileMainContent(state: ProfileState, eventHandler: (ProfileEvent) -> Unit) {
    Column(
        Modifier
            .background(
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            // Имя пользователя
            Text(
                text = "Любитель покушать",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            // Кнопка DropdownMenu
            IconButton(
                onClick = { eventHandler.invoke(ProfileEvent.OnDropdownMenuClick) },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painterResource(R.drawable.ic_dropdown_menu),
                    modifier = Modifier,
                    contentDescription = "icon_dropdown_menu",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        ProfileDropDownMenu(
            state = state,
            eventHandler = eventHandler,
            modifier = Modifier
                .align(Alignment.End)
                .offset(x = (-120).dp)
        )

        LocationText(
            city = "Казань", country = "Россия",
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            arrangement = Arrangement.Center
        )

        ProfileStatistic(state = state, eventHandler = eventHandler, modifier = Modifier.padding(top = 20.dp))

        SwitchButton(
            onClick = {  },
            pressed = false,
            text = "Редактировать",
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}


@Composable
private fun ProfileStatistic(
    state: ProfileState,
    eventHandler: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {},
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "12",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = "Избранное",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    // TODO
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "5",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = "Заказы",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun ProfileDropDownMenu(
    state: ProfileState,
    eventHandler: (ProfileEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        DropdownMenu(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.secondary)
                .clip(RoundedCornerShape(8.dp)),
            expanded = state.isDropdownMenuExpanded,
            onDismissRequest = {
                eventHandler.invoke(ProfileEvent.OnCloseDropdownMenu)
            },
        ) {
            //CustomDropDownItem(text = stringResource(id = R.string.sign_out)) { eventHandler.invoke(ProfileEvent.OnSignOutButtonClick) }
        }
    }
}