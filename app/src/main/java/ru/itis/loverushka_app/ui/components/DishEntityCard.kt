package ru.itis.loverushka_app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.itis.loverushka_app.domain.model.Dish


@Composable
fun DishEntityCard(modifier: Modifier, dish: Dish, onClick: () -> Unit) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ){
            Column (
                modifier = Modifier
                    .padding(all = 12.dp),
            ){
                AsyncImage(
                    modifier = Modifier,
                    model = dish.dishPhoto,
                    contentDescription = "photo",
                )
                Spacer(modifier = Modifier.width(width = 20.dp))
                Text(
                    text = dish.dishName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )

            }
        }
    }
}