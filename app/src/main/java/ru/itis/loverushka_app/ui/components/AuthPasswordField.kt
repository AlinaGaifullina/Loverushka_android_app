package ru.itis.loverushka_app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.itis.loverushka_app.R


@Composable
fun AuthPasswordField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    passwordVisible: Boolean,
    onVisibleChange: () -> Unit
) {
    Column {

        TextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            label = { Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary
            ) },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
            ),
            trailingIcon = {
                val image = if (passwordVisible) painterResource(id = R.drawable.ic_show) else painterResource(id = R.drawable.ic_hide)
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = onVisibleChange, modifier = Modifier.padding(end = 8.dp)) {
                    Icon(
                        painter = image,
                        description,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        )
    }
}