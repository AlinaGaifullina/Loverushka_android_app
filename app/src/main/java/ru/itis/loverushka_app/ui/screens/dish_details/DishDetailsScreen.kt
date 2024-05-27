package ru.itis.loverushka_app.ui.screens.dish_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import ru.itis.loverushka_app.R
import ru.itis.loverushka_app.ui.components.BuyButton


@Composable
fun DishDetailsScreen(
    navController: NavController,
    viewModel: DishDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(Unit) {
        eventHandler.invoke(DishDetailsEvent.OnLaunch)
    }

    LaunchedEffect(action) {
        when (action) {
            DishDetailsSideEffect.NavigateBack -> navController.navigateUp()
            else -> Unit
        }
    }
    val listState = rememberScrollState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 20.dp, end = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(listState)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    painterResource(R.drawable.ic_back),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                        .size(28.dp)
                        .clickable { eventHandler.invoke(DishDetailsEvent.OnBackBtnClick) },
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Icon(
                    painterResource(
                        if (state.isFavouriteDish)
                            R.drawable.heart_fill else R.drawable.heart_outline
                    ),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 16.dp)
                        .size(30.dp)
                        .clickable { eventHandler.invoke(DishDetailsEvent.OnFavouriteChange) },
                    contentDescription = "favourite",
                    tint = if (state.isFavouriteDish)
                        MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 22.dp, bottom = 8.dp),
                text = state.data.dishName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier,
                    model = state.data.photo,
                    contentDescription = "dishPhoto",
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    thickness = 3.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }


            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.favouritesId.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

//        Row (
//            modifier = Modifier.padding(vertical = 8.dp),
//            verticalAlignment = Alignment.Bottom
//        ) {
//            Text(
//                text = stringResource(id = R.string.rating_imdb),
//                style = MaterialTheme.typography.titleSmall,
//                color = MaterialTheme.colorScheme.secondary
//            )
//            Text(
//                modifier = Modifier.padding(start = 20.dp),
//                text = state.data.rating.ratingImdb,
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.primary
//            )
//        }

//        Text(
//            modifier = Modifier.padding(vertical = 16.dp),
//            text = stringResource(id = R.string.description),
//            style = MaterialTheme.typography.titleSmall,
//            color = MaterialTheme.colorScheme.secondary
//        )
//        Text(
//            text = state.data.description ?: "",
//            style = MaterialTheme.typography.bodyMedium,
//            color = MaterialTheme.colorScheme.primary
//        )
        }

        Box(
            modifier = Modifier.fillMaxSize(). padding(bottom = 64.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            BuyButton(
                onBuyClick = { /*TODO*/ },
                onPlusMinusClick = { /*TODO*/ },
                isPressed = true,
                dishNumber = 1,
                modifier = Modifier
            )
        }

        //Spacer(modifier = Modifier.height(40.dp))
    }
}