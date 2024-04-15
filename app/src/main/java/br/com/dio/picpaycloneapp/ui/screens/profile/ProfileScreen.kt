package br.com.dio.picpaycloneapp.ui.screens.profile

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dio.picpaycloneapp.R

@Composable
fun ProfileScreen() {
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
                    text = "login",
                    modifier = Modifier.padding(top = 4.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = "completeName",
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
                text = "login",
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            )

            Divider(modifier = Modifier.padding(top = 16.dp))

            Text(
                text = "Meu número",
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )

            Text(
                text = "phoneNumber",
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            )

            Divider(modifier = Modifier.padding(top = 16.dp))

            Text(
                text = "Meu e-mail",
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )

            Text(
                text = "email",
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            )

            Divider(modifier = Modifier.padding(top = 16.dp))

            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                ) {
                Text(
                    text = "Sair".uppercase(),
                    style = TextStyle(color = Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            
            Divider(modifier = Modifier.padding(top = 4.dp))
        }
    }
}
