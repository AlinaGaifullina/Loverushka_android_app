package ru.itis.loverushka_app.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.itis.loverushka_app.R
import ru.itis.loverushka_app.ui.components.AuthBottomText
import ru.itis.loverushka_app.ui.components.AuthButton
import ru.itis.loverushka_app.ui.components.AuthPasswordField
import ru.itis.loverushka_app.ui.components.AuthTextField
import ru.itis.loverushka_app.ui.components.PhoneTextField
import ru.itis.loverushka_app.ui.navigation.BottomNavigationItem

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(action) {
        when (action) {
            RegisterSideEffect.NavigateProfile -> navController.navigate(BottomNavigationItem.Profile.graph)
            RegisterSideEffect.NavigateLogin -> navController.navigateUp()
            else -> Unit
        }
    }

    RegisterScreenContent(state = state, eventHandler = eventHandler)
}

@Composable
private fun RegisterScreenContent(state: RegisterState, eventHandler: (RegisterEvent) -> Unit) {


    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 40.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary
            )

            if(state.errors.isNotEmpty()) {
                state.errors.forEach {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Column {
            PhoneTextField(
                stringResource(id = R.string.phone),
                state.phone
            ) { eventHandler.invoke(RegisterEvent.OnUsernameChange(it)) }

            Spacer(modifier = Modifier.height(20.dp))
            AuthTextField(
                stringResource(id = R.string.first_name),
                state.firstName
            ) { eventHandler.invoke(RegisterEvent.OnFirstNameChange(it)) }

            Spacer(modifier = Modifier.height(20.dp))
            AuthTextField(
                stringResource(id = R.string.last_name),
                state.lastName
            ) { eventHandler.invoke(RegisterEvent.OnLastNameChange(it)) }

            Spacer(modifier = Modifier.height(20.dp))
            AuthPasswordField(
                stringResource(id = R.string.password),
                state.password,
                { eventHandler.invoke(RegisterEvent.OnPasswordChange(it)) },
                state.passwordVisible,
                { eventHandler.invoke(RegisterEvent.OnPasswordVisibilityChange) })

            Spacer(modifier = Modifier.height(20.dp))
            AuthPasswordField(
                stringResource(id = R.string.repeat_pass),
                state.confirmPassword,
                { eventHandler.invoke(RegisterEvent.OnConfirmPasswordChange(it)) },
                state.passwordVisible,
                { eventHandler.invoke(RegisterEvent.OnPasswordVisibilityChange) })
        }

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthButton(
                onClick = { eventHandler.invoke(RegisterEvent.OnRegisterButtonClick) },
                text = stringResource(id = R.string.sign_up_btn),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(60.dp)

            )

            AuthBottomText(
                stringResource(id = R.string.have_acc),
                stringResource(id = R.string.sign_in)
            ) { eventHandler.invoke(RegisterEvent.OnLoginButtonCLick) }
        }
    }
}