package br.com.dio.picpaycloneapp.ui.bottom_nav

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.dio.picpaycloneapp.data.models.User
import br.com.dio.picpaycloneapp.ui.screens.home.HomeScreen
import br.com.dio.picpaycloneapp.ui.screens.home.HomeViewModel
import br.com.dio.picpaycloneapp.ui.screens.login.LoginViewModel
import br.com.dio.picpaycloneapp.ui.screens.payment.PaymentScreen
import br.com.dio.picpaycloneapp.ui.screens.payment.PaymentViewModel
import br.com.dio.picpaycloneapp.ui.screens.profile.ProfileScreen
import br.com.dio.picpaycloneapp.ui.screens.transaction.TransactionScreen
import br.com.dio.picpaycloneapp.ui.screens.transaction.TransactionViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Composable
fun BottomNavHost(navController: NavController, modifier: Modifier, goToLogin: () -> Unit) {
    val loginViewModel: LoginViewModel = viewModel(
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
    val loginState = loginViewModel.state.collectAsState()
    val loggedUser = loginState.value.loggedUser

    NavHost(
        navController = navController as NavHostController,
        startDestination = BottomNavScreen.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomNavScreen.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                homeViewModel = homeViewModel,
                loggedUser = loggedUser,
                goToLogin = goToLogin
            )
        }
        composable(route = BottomNavScreen.Payment.route) {
            val paymentViewModel = hiltViewModel<PaymentViewModel>()
            PaymentScreen(
                navController = navController,
                paymentViewModel = paymentViewModel,
                loggedUser = loggedUser
            )
        }
        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen(navController = navController, loggedUser = loggedUser) {
                loginViewModel.logout()
            }
        }
        composable(
            route = BottomNavScreen.Transaction.route,
            arguments = listOf(navArgument("destinationUser") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val gson: Gson = GsonBuilder().create()
            val userJson = navBackStackEntry.arguments?.getString("destinationUser")
            val user = gson.fromJson(userJson, User::class.java)
            val transactionViewModel = hiltViewModel<TransactionViewModel>()
            TransactionScreen(
                navController = navController,
                transactionViewModel = transactionViewModel,
                loggedUser = loggedUser,
                destinationUser = user
            )
        }
    }
}
