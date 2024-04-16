package br.com.dio.picpaycloneapp.ui.screens.profile

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.dio.picpaycloneapp.R
import br.com.dio.picpaycloneapp.data.models.User
import br.com.dio.picpaycloneapp.ui.bottom_nav.BottomNavScreen
import br.com.dio.picpaycloneapp.ui.screens.login.LoginViewModel

@Composable
fun ProfileScreen(
    navController: NavController, loginViewModel: LoginViewModel = viewModel(
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
) {
    val loginState = loginViewModel.state.collectAsState()

    val loggedUser: User = if (loginState.value.isLoggedUser) {
        loginState.value.loggedUser!!
    } else {
        navController.navigate(BottomNavScreen.Home.route)
        return
    }

    fun onExit() {
        loginViewModel.logout()
        navController.navigate(BottomNavScreen.Home.route)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(top = 32.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.ic_contact
                    ),
                    contentDescription = "Avatar",
                )

                Text(
                    text = loggedUser.login,
                    modifier = Modifier.padding(top = 4.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = loggedUser.completeName,
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.Normal)
                )
            }

            Text(
                text = "Meu PicPay",
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )

            Text(
                text = loggedUser.login,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            )

            Divider(modifier = Modifier.padding(top = 16.dp))

            Text(
                text = "Meu número",
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )

            Text(
                text = loggedUser.phoneNumber,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            )

            Divider(modifier = Modifier.padding(top = 16.dp))

            Text(
                text = "Meu e-mail",
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )

            Text(
                text = loggedUser.email,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            )

            Divider(modifier = Modifier.padding(top = 16.dp))

            TextButton(
                onClick = {
                    onExit()
                },
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            ) {
                Text(
                    text = "Sair".uppercase(),
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Divider(modifier = Modifier.padding(top = 4.dp))
        }
    }
}
