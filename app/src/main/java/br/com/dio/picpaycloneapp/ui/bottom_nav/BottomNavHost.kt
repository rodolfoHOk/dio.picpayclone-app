package br.com.dio.picpaycloneapp.ui.bottom_nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.dio.picpaycloneapp.ui.screens.home.HomeScreen
import br.com.dio.picpaycloneapp.ui.screens.payment.PaymentScreen
import br.com.dio.picpaycloneapp.ui.screens.profile.ProfileScreen
import br.com.dio.picpaycloneapp.ui.screens.transaction.TransactionScreen

@Composable
fun BottomNavHost(navController: NavController, modifier: Modifier, goToLogin: () -> Unit) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = BottomNavScreen.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen(goToLogin = goToLogin)
        }
        composable(route = BottomNavScreen.Payment.route) {
            PaymentScreen(navController = navController)
        }
        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen()
        }
        composable(
            route = "${BottomNavScreen.Transaction.route}/{destinationLogin}",
            arguments = listOf(navArgument("destinationLogin") { type = NavType.StringType })
        ) { navBackStackEntry ->
            TransactionScreen(
                navController = navController,
                destinationLogin = navBackStackEntry.arguments?.getString("destinationLogin")
            )
        }
    }
}
