package br.com.dio.picpaycloneapp.ui.bottom_nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController, bottomBarVisibilityState: MutableState<Boolean>) {
    val items = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Payment,
        BottomNavScreen.Profile
    )
    AnimatedVisibility(
        visible = bottomBarVisibilityState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.border(width = 1.dp, MaterialTheme.colorScheme.surface),
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            items.forEach { screen ->
                val isSelectedItem = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true

                BottomNavigationItem(
                    selected = isSelectedItem,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    icon = {
                        Icon(
                            painterResource(id = screen.iconId),
                            contentDescription = stringResource(id = screen.resourceId),
                            tint = if (isSelectedItem) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = screen.resourceId),
                            color = if (isSelectedItem)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onPrimary
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
}
