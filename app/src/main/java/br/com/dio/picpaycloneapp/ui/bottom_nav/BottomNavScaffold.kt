package br.com.dio.picpaycloneapp.ui.bottom_nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BottomNavScaffold(navController: NavController, goToLogin: () -> Unit) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { paddingValues ->
        BottomNavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            goToLogin = goToLogin
        )
    }
}
