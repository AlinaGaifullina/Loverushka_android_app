package ru.itis.loverushka_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun DishInCartCard(
    modifier: Modifier,
    dish: Dish,
    numberOfDishes: Int,
    isCheckboxOn: Boolean,
    onCardClick: () -> Unit,
    onCheckboxClick: () -> Unit,
    onBinClick: () -> Unit,
    onPlusMinusClick: (sign: Boolean) -> Unit,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClick() }
        ){
            Icon(
                painterResource(R.drawable.ic_bin),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clickable { onBinClick() }
                    .size(24.dp)
                    .align(Alignment.TopEnd),
                contentDescription = "icon",
                tint = MaterialTheme.colorScheme.surface
            )
            Column (
                modifier = Modifier
                    .padding(all = 16.dp),
            ){
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    // чекбокс
                    Icon(
                        painterResource(
                            if(isCheckboxOn) R.drawable.ic_checkbox_checked else R.drawable.ic_checkbox
                        ),
                        modifier = Modifier
                            .size(36.dp)
                            .padding(end = 8.dp)
                            .clickable { onCheckboxClick() },
                        contentDescription = "icon",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                    // Картинка
                    Box(
                        modifier = Modifier
                            .height(91.dp)
                            .width(125.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .height(86.dp)
                                .width(120.dp)
                                .align(Alignment.Center),
                            model = dish.dishPhoto,
                            contentDescription = "photo",
                            clipToBounds = true,
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    // цена и название
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "${dish.dishPrice} р.",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                        Text(
                            text = dish.dishName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }

                Row (
                    modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        onClick = {},
                        modifier = modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row (
                            modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painterResource(
                                    R.drawable.ic_minus
                                ),
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable { onPlusMinusClick(false) },
                                contentDescription = "icon",
                                tint = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.3f)
                            )
                            Text(
                                text = numberOfDishes.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSecondary
                            )

                            Icon(
                                painterResource(
                                    R.drawable.ic_plus
                                ),
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable { onPlusMinusClick(true) },
                                contentDescription = "icon",
                                tint = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }
        }
    }
}

