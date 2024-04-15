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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dio.picpaycloneapp.data.LoggedUser
import br.com.dio.picpaycloneapp.ui.LocalSnackbarHostState
import br.com.dio.picpaycloneapp.ui.components.TransactionItem
import br.com.dio.picpaycloneapp.ui.utils.decimalFormatter

@Composable
fun HomeScreen(goToLogin: () -> Unit, homeViewModel: HomeViewModel) {

    if (LoggedUser.isNotLoggedUser()) {
        goToLogin()
    } else {
        homeViewModel.fetchLoggerUserBalance()
        homeViewModel.fetchLoggerUserTransactions()
    }

    val homeUiState = homeViewModel.state.collectAsState()

    val snackbarHostState = LocalSnackbarHostState.current
    LaunchedEffect(Unit) {
        homeViewModel.action.collect { action ->
            when(action) {
                is HomeUiAction.BalanceError -> {
                    snackbarHostState.showSnackbar(action.message)
                }

                is HomeUiAction.TransactionsError -> {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
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

                if (homeUiState.value.isLoadingBalance)
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                else
                    Text(
                        text = "R$ ${decimalFormatter.format(homeUiState.value.balance.balance)}",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "Atividades:",
                modifier = Modifier.padding(start = 16.dp),
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            )

            if (homeUiState.value.isLoadingTransactions)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            else
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(homeUiState.value.pageTransactions.content) { transaction ->
                        TransactionItem(transaction)
                    }
                }
        }
    }
}
