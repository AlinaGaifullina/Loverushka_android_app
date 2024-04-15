package ru.itis.loverushka_app.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.itis.loverushka_app.ui.navigation.AuthScreen
import ru.itis.loverushka_app.ui.navigation.BottomNavigationItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.itis.loverushka_app.R
import ru.itis.loverushka_app.ui.components.AuthBottomText
import ru.itis.loverushka_app.ui.components.AuthButton
import ru.itis.loverushka_app.ui.components.AuthPasswordField
import ru.itis.loverushka_app.ui.components.AuthTextField

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(action) {
        when (action) {
            LoginSideEffect.NavigateProfile -> navController.navigate(BottomNavigationItem.Profile.graph)
            LoginSideEffect.NavigateRegister -> navController.navigate(AuthScreen.SignUp.route)
            else -> Unit
        }
    }

    LoginMainContent(state = state, eventHandler = eventHandler)
}

@Composable
fun LoginMainContent(state: LoginState, eventHandler: (LoginEvent) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp, vertical = 20.dp),
            //.padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
            if(state.showLoginError){
                Text(
                    text = state.loginError,
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        Column(
        ) {
            AuthTextField(stringResource(id = R.string.phone), state.phone) {
                eventHandler.invoke(LoginEvent.OnUsernameChange(it))
            }

            Spacer(modifier = Modifier.height(32.dp))
            AuthPasswordField(
                stringResource(id = R.string.password), state.password,
                onChange = { eventHandler.invoke(LoginEvent.OnPasswordChange(it)) },
                state.passwordVisible,
                onVisibleChange = { eventHandler.invoke(LoginEvent.OnPasswordVisibilityChange) }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthButton(
                onClick = { eventHandler.invoke(LoginEvent.OnLoginButtonClick) },
                text = stringResource(id = R.string.sign_in_btn),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
            AuthBottomText(
                stringResource(id = R.string.no_acc),
                stringResource(id = R.string.sign_up_btn)
            ) { eventHandler.invoke(LoginEvent.OnRegisterButtonCLick) }
        }
    }
}