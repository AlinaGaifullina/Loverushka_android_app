package ru.itis.loverushka_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.itis.loverushka_app.R

@Composable
fun LocationText(city: String?, country: String?, modifier: Modifier = Modifier, arrangement: Arrangement.Horizontal) {
    if (city == null && country == null) return
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = arrangement
    ) {
        Icon(
            painterResource(R.drawable.baseline_add_location_24),
            contentDescription = "icon_location",
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = if (city == null) country!! else (if (country == null) city else "${city}, ${country}"),
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}