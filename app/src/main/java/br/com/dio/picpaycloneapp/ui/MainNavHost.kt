package br.com.dio.picpaycloneapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.dio.picpaycloneapp.ui.bottom_nav.BottomNavScaffold
import br.com.dio.picpaycloneapp.ui.login.LoginScreen

@Composable
fun MainNavHost(
    mainNavController: NavHostController,
    bottomBarNavController: NavHostController,
    paddingValues: PaddingValues
) {

    fun goToLogin() {
        mainNavController.navigate(MainNavScreen.Login.route)
    }

    NavHost(
        navController = mainNavController,
        startDestination = MainNavScreen.BottomNavScreens.route,
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        composable(MainNavScreen.Login.route) {
            LoginScreen()
        }
        composable(MainNavScreen.BottomNavScreens.route) {
            BottomNavScaffold(navController = bottomBarNavController) {
                goToLogin()
            }
        }
    }
}
