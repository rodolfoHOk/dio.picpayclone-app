package br.com.dio.picpaycloneapp.ui.bottom_nav

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Payment,
        BottomNavScreen.Profile
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = {
                    Icon(
                        painterResource(id = screen.iconId),
                        contentDescription = stringResource(id = screen.resourceId)
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = screen.resourceId),
                        color = Color.White
                    )
                },
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
