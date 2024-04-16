package br.com.dio.picpaycloneapp.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.dio.picpaycloneapp.data.models.User
import br.com.dio.picpaycloneapp.ui.LocalSnackbarHostState
import br.com.dio.picpaycloneapp.ui.components.MyBalance
import br.com.dio.picpaycloneapp.ui.components.TransactionList

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    loggedUser: User?,
    goToLogin: () -> Unit
) {

    loggedUser?.let {
        homeViewModel.fetchLoggerUserBalance(loggedUser.login)
        val transactionsFlow = homeViewModel.getTransactions(loggedUser.login)

        val homeState = homeViewModel.state.collectAsState()

        val snackbarHostState = LocalSnackbarHostState.current
        LaunchedEffect(Unit) {
            homeViewModel.action.collect { action ->
                when (action) {
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
                MyBalance(
                    isLoadingBalance = homeState.value.isLoadingBalance,
                    balance = homeState.value.balance.balance
                )

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "Atividades:",
                    modifier = Modifier.padding(start = 16.dp),
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                )

                TransactionList(transactionsFlow = transactionsFlow)
            }
        }
    } ?: goToLogin()
}
