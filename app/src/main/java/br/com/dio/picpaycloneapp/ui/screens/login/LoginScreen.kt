package br.com.dio.picpaycloneapp.ui.screens.login

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dio.picpaycloneapp.R
import br.com.dio.picpaycloneapp.ui.components.StyledTextField

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
) {

    val loginUiState = loginViewModel.state.collectAsState()

    fun onLoginClick() {
        loginViewModel.login()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.picpay_logo),
                contentDescription = "PicPay",
                modifier = Modifier.height(200.dp)
            )

            StyledTextField(
                label = "Usuário",
                value = loginUiState.value.username,
                onValueChange = { loginViewModel.updateUsername(it) }
            )

            StyledTextField(
                label = "Senha",
                value = loginUiState.value.password,
                onValueChange = { loginViewModel.updatePassword(it) },
                keyboardType = KeyboardType.Password
            )

            Button(
                onClick = { onLoginClick() },
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.background
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (loginUiState.value.isLoading)
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                else
                    Text(text = "Entrar".uppercase())
            }
        }
    }
}
