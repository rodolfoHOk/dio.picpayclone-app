package br.com.dio.picpaycloneapp.features

import androidx.annotation.StringRes
import br.com.dio.picpaycloneapp.R

sealed class MainNavScreen(val route: String, @StringRes var resourceId: Int) {
    data object Login: MainNavScreen("login", R.string.login)
    data object BottomNavScreens: MainNavScreen("bottom_nav_screens", R.string.bottom_nav_screens)
}
