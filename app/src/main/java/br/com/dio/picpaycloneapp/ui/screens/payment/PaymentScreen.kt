package br.com.dio.picpaycloneapp.ui.screens.payment

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.navigation.NavController
import br.com.dio.picpaycloneapp.data.models.User
import br.com.dio.picpaycloneapp.ui.LocalSnackbarHostState
import br.com.dio.picpaycloneapp.ui.bottom_nav.BottomNavScreen
import br.com.dio.picpaycloneapp.ui.components.ContactItem
import br.com.dio.picpaycloneapp.ui.screens.login.LoginViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Composable
fun PaymentScreen(
    navController: NavController,
    paymentViewModel: PaymentViewModel,
    loginViewModel: LoginViewModel = viewModel(
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
) {
    val loginState = loginViewModel.state.collectAsState()
    val paymentUiState = paymentViewModel.state.collectAsState()

    if (loginState.value.isLoggedUser) {
        if (!paymentUiState.value.isUserContacts) {
            val loggedUserLogin = loginState.value.loggedUser!!.login
            paymentViewModel.fetchUserContacts(loggedUserLogin)
        }
    } else {
        navController.navigate(BottomNavScreen.Home.route)
    }

    val userContacts = paymentUiState.value.contacts

    val gson: Gson = GsonBuilder().create()

    val snackbarHostState = LocalSnackbarHostState.current
    LaunchedEffect(Unit) {
        paymentViewModel.action.collect { action ->
            when (action) {
                is PaymentUiAction.ContactsError -> {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Contatos:",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )

            if (paymentUiState.value.isLoading)
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
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(userContacts) { user ->
                        ContactItem(username = user.login, completeName = user.completeName) {
                            val userJson = gson.toJson(user, User::class.java)
                            navController.navigate(
                                BottomNavScreen.Transaction.route.replace(
                                    "{destinationUser}",
                                    userJson.toString()
                                )
                            )
                        }
                    }
                }
        }
    }
}
