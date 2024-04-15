package br.com.dio.picpaycloneapp.ui.bottom_nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavScaffold(
    navController: NavController,
    goToLogin: () -> Unit
) {
    val bottomBarVisibilityState = rememberSaveable { (mutableStateOf(true)) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route: String = navBackStackEntry?.destination?.route ?: ""
    when(route) {
        BottomNavScreen.Home.route -> bottomBarVisibilityState.value = true
        BottomNavScreen.Payment.route -> bottomBarVisibilityState.value = true
        BottomNavScreen.Profile.route -> bottomBarVisibilityState.value = true
        BottomNavScreen.Transaction.route -> bottomBarVisibilityState.value = false
        else -> bottomBarVisibilityState.value = true
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                bottomBarVisibilityState = bottomBarVisibilityState
            )
        }
    ) { paddingValues ->
        BottomNavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            goToLogin = goToLogin
        )
    }
}
