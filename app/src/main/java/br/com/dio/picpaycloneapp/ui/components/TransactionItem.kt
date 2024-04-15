package br.com.dio.picpaycloneapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TransactionItem() {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.padding(bottom = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                ) {
                    Text(
                        text = "V",
                        color = MaterialTheme.colorScheme.background,
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                Text(
                    text = "João Vitor Freitas",
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = "pagou",
                    modifier = Modifier.padding(start = 4.dp),
                    style = TextStyle(fontWeight = FontWeight.Normal)
                )

                Text(
                    text = "Cícero Moura",
                    modifier = Modifier.padding(start = 4.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, bottom = 16.dp),
            ) {
                Text(
                    text = "R$ 10,00",
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = "|",
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.Normal)
                )

                Text(
                    text = "dia 15/04/2024",
                    modifier = Modifier.padding(start = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.Normal)
                )
            }
        }
    }
}