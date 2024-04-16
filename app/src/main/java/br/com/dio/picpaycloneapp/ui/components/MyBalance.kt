package br.com.dio.picpaycloneapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dio.picpaycloneapp.ui.utils.decimalFormatter

@Composable
fun MyBalance(isLoadingBalance: Boolean, balance: Double) {
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

        if (isLoadingBalance)
            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
        else
            Text(
                text = "R$ ${decimalFormatter.format(balance)}",
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
    }
}
