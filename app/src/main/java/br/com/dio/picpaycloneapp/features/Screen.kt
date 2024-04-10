package br.com.dio.picpaycloneapp.features

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.dio.picpaycloneapp.R

sealed class Screen(val route: String, @StringRes var resourceId: Int, val icon: ImageVector) {
    data object Home: Screen("home", R.string.home, Icons.Rounded.Home)
    data object Login: Screen("login", R.string.login, Icons.Rounded.AccountCircle)
}
