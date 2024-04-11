package br.com.dio.picpaycloneapp.features.bottom_nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import br.com.dio.picpaycloneapp.R

sealed class BottomNavScreen(val route: String, @StringRes var resourceId: Int, @DrawableRes val iconId: Int) {
    data object Home: BottomNavScreen("home", R.string.home, R.drawable.ic_home)
    data object Payment: BottomNavScreen("payment", R.string.payment, R.drawable.ic_attach_money)
    data object Profile: BottomNavScreen("profile", R.string.profile, R.drawable.ic_account_circle)
}
