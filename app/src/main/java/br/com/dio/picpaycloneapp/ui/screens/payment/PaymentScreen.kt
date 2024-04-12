package br.com.dio.picpaycloneapp.ui.screens.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.dio.picpaycloneapp.data.User
import br.com.dio.picpaycloneapp.ui.bottom_nav.BottomNavScreen
import br.com.dio.picpaycloneapp.ui.components.ContactItem
import com.google.gson.Gson

@Composable
fun PaymentScreen(navController: NavController) {
    val users: List<User> = listOf(
        User(
            login = "joaovf",
            completeName = "João Vitor Freitas",
            balance = 500.00
        ),
        User(
            login = "cicerom",
            completeName = "Cícero Moura",
            balance = 300.00
        ),
        User(
            login = "jpauloa",
            completeName = "Jośe Paulo Alencar",
            balance = 100.00,
        )
    )

    val gson = Gson()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Contatos:",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(users) { user ->
                    ContactItem(username = user.login, completeName = user.completeName) {
                        navController.navigate(
                            "${BottomNavScreen.Transaction.route}/${gson.toJson(user)}"
                        )
                    }
                }
            }
        }
    }
}
