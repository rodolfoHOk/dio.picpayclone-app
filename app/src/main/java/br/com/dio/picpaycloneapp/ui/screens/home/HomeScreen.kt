package br.com.dio.picpaycloneapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dio.picpaycloneapp.data.LoggedUser
import br.com.dio.picpaycloneapp.ui.components.TransactionItem

@Composable
fun HomeScreen(goToLogin: () -> Unit) {
    if (LoggedUser.isNotLoggedUser()) {
        goToLogin()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Meu saldo",
                    style = TextStyle(fontSize = 12.sp)
                )
                
                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = "R$ 0,00",
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )

                CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
            }
            
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "Atividades:",
                modifier = Modifier.padding(start = 16.dp),
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            )

            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(listOf(1, 2, 3)) {item ->
                    TransactionItem()
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
