package br.com.dio.picpaycloneapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import br.com.dio.picpaycloneapp.ui.theme.PicPayCloneAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PicPayCloneAppTheme {
                val mainNavController = rememberNavController()
                val bottomBarNavController = rememberNavController()

                MainNavHost(
                    mainNavController = mainNavController,
                    bottomBarNavController = bottomBarNavController
                )
            }
        }
    }
}
