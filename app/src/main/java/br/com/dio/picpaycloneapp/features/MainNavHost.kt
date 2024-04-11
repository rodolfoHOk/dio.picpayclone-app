package br.com.dio.picpaycloneapp.features

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.dio.picpaycloneapp.features.bottom_nav.BottomNavScaffold
import br.com.dio.picpaycloneapp.features.login.LoginScreen

@Composable
fun MainNavHost(mainNavController: NavHostController, bottomBarNavController: NavHostController) {

    fun goToLogin() {
        mainNavController.navigate(MainNavScreen.Login.route)
    }

    NavHost(
        navController = mainNavController,
        startDestination = MainNavScreen.BottomNavScreens.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(MainNavScreen.Login.route) {
            LoginScreen(navController = mainNavController)
        }
        composable(MainNavScreen.BottomNavScreens.route) {
            BottomNavScaffold(navController = bottomBarNavController) {
                goToLogin()
            }
        }
    }
}
