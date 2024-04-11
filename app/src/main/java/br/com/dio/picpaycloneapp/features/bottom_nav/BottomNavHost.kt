package br.com.dio.picpaycloneapp.features.bottom_nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.dio.picpaycloneapp.features.bottom_nav.home.HomeScreen
import br.com.dio.picpaycloneapp.features.bottom_nav.payment.PaymentScreen
import br.com.dio.picpaycloneapp.features.bottom_nav.profile.ProfileScreen

@Composable
fun BottomNavHost(navController: NavController, modifier: Modifier, logout: () -> Unit) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = BottomNavScreen.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavScreen.Home.route) { HomeScreen(logout) }
        composable(BottomNavScreen.Payment.route) { PaymentScreen() }
        composable(BottomNavScreen.Profile.route) { ProfileScreen() }
    }
}
