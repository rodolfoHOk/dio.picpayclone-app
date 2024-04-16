package br.com.dio.picpaycloneapp.ui.screens.home

import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.dio.picpaycloneapp.ui.LocalSnackbarHostState
import br.com.dio.picpaycloneapp.ui.components.MyBalance
import br.com.dio.picpaycloneapp.ui.components.TransactionList
import br.com.dio.picpaycloneapp.ui.screens.login.LoginViewModel

@Composable
fun HomeScreen(
    goToLogin: () -> Unit,
    homeViewModel: HomeViewModel,
    loginViewModel: LoginViewModel = viewModel(
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
) {
    val loginState = loginViewModel.state.collectAsState()

    val loggedUserLogin: String = if (loginState.value.isLoggedUser) {
        val loggedUserLogin = loginState.value.loggedUser!!.login
        homeViewModel.fetchLoggerUserBalance(loggedUserLogin)
        loggedUserLogin
    } else {
        goToLogin()
        return
    }

    val homeUiState = homeViewModel.state.collectAsState()
    val transactionsFlow = homeViewModel.getTransactions(loggedUserLogin)

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
                isLoadingBalance = homeUiState.value.isLoadingBalance,
                balance = homeUiState.value.balance.balance
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
}
