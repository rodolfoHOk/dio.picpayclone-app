package br.com.dio.picpaycloneapp.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.dio.picpaycloneapp.R
import br.com.dio.picpaycloneapp.components.StyledTextField

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") } // temp: to viewmodel
    var password by remember { mutableStateOf("") } // temp: to viewmodel

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.colorPrimary))
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
                value = username,
                onValueChange = { username = it }
            )

            StyledTextField(
                label = "Senha",
                value = password,
                onValueChange = { password = it },
                keyboardType = KeyboardType.Password
            )

            Button(
                onClick = { /*TODO*/ },
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    Color.White,
                    contentColor = Color.Black,
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Entrar".uppercase())
            }
        }
    }
}
