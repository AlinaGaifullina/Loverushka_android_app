package ru.itis.loverushka_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.itis.loverushka_app.R
import ru.itis.loverushka_app.domain.model.Dish


@Composable
fun DishEntityCard(
    modifier: Modifier,
    dish: Dish,
    numberOfDishes: Int,
    isBuyButtonPressed: Boolean,
    favouritesId: List<Int>,
    onCardClick: () -> Unit,
    onLikeClick: () -> Unit,
    onBuyClick: () -> Unit,
    onPlusMinusClick: (sign: Boolean) -> Unit,
    authorPhotoUrl: String
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClick() }
        ){
            Column (
                modifier = Modifier
                    .padding(all = 16.dp),
            ){
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = dish.dishName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                    Icon(
                        painterResource(
                            if(favouritesId.contains(dish.dishId)) R.drawable.heart_fill else R.drawable.heart_outline
                        ),
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { onLikeClick() },
                        contentDescription = "icon",
                        tint = if(favouritesId.contains(dish.dishId)) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        model = dish.dishPhoto,
                        contentDescription = "photo",
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .padding(3.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .size(40.dp),
                            model = authorPhotoUrl,
                            contentDescription = "author photo",
                            contentScale = ContentScale.Crop,
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = dish.dishAuthor,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ингредиенты: ${dish.ingredients}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )
                Spacer(modifier = Modifier.height(8.dp))


                BuyButton(
                    onBuyClick = { onBuyClick() },
                    onPlusMinusClick = { onPlusMinusClick(
                        if (it){
                            true
                        } else false
                    ) },
                    isPressed = isBuyButtonPressed,
                    dishNumber = numberOfDishes
                )
            }
        }
    }
}