package br.com.dio.picpaycloneapp.ui.bottom_nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.dio.picpaycloneapp.data.User
import br.com.dio.picpaycloneapp.ui.screens.home.HomeScreen
import br.com.dio.picpaycloneapp.ui.screens.home.HomeViewModel
import br.com.dio.picpaycloneapp.ui.screens.payment.PaymentScreen
import br.com.dio.picpaycloneapp.ui.screens.payment.PaymentViewModel
import br.com.dio.picpaycloneapp.ui.screens.profile.ProfileScreen
import br.com.dio.picpaycloneapp.ui.screens.transaction.TransactionScreen
import br.com.dio.picpaycloneapp.ui.screens.transaction.TransactionViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Composable
fun BottomNavHost(navController: NavController, modifier: Modifier, goToLogin: () -> Unit) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = BottomNavScreen.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomNavScreen.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(goToLogin = goToLogin, homeViewModel = homeViewModel)
        }
        composable(route = BottomNavScreen.Payment.route) {
            val paymentViewModel = hiltViewModel<PaymentViewModel>()
            PaymentScreen(navController = navController, paymentViewModel)
        }
        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen()
        }
        composable(
            route = "${BottomNavScreen.Transaction.route}/{destinationUser}",
            arguments = listOf(navArgument("destinationUser") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val gson: Gson = GsonBuilder().create()
            val userJson = navBackStackEntry.arguments?.getString("destinationUser")
            val user = gson.fromJson(userJson, User::class.java)
            val transactionViewModel = hiltViewModel<TransactionViewModel>()
            TransactionScreen(
                navController = navController,
                destinationUser = user,
                transactionViewModel = transactionViewModel
            )
        }
    }
}
