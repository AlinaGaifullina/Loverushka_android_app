package ru.itis.loverushka_app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.itis.loverushka_app.R

@Composable
fun BuyButton(
    onBuyClick: () -> Unit,
    onPlusMinusClick: (sign: Boolean) -> Unit,
    isPressed: Boolean,
    dishNumber: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onBuyClick,
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp),
        enabled = !isPressed,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed)
                MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.8f)
            else
                MaterialTheme.colorScheme.surfaceTint,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if(isPressed) {
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
                        .size(48.dp)
                        .padding(end = 8.dp)
                        .clickable { onPlusMinusClick(false) },
                    contentDescription = "icon",
                    tint = MaterialTheme.colorScheme.tertiary
                )

                Column (
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dishNumber.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "В корзине",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Icon(
                    painterResource(
                        R.drawable.ic_plus
                    ),
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 8.dp)
                        .clickable { onPlusMinusClick(true) },
                    contentDescription = "icon",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        } else {
            Icon(
                painterResource(
                    R.drawable.ic_cart
                ),
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 8.dp)
                    .clickable { onBuyClick() },
                contentDescription = "icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Купить",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}