package br.com.dio.picpaycloneapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import br.com.dio.picpaycloneapp.ui.screens.login.LoginUiAction
import br.com.dio.picpaycloneapp.ui.screens.login.LoginViewModel
import br.com.dio.picpaycloneapp.ui.theme.PicPayCloneAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel by viewModels<LoginViewModel>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PicPayCloneAppTheme {
                val mainNavController = rememberNavController()
                val bottomBarNavController = rememberNavController()

                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { paddingValues ->
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val lifecycle = remember(lifecycleOwner) { lifecycleOwner.lifecycle }

                    LaunchedEffect(lifecycle) {
                        lifecycleScope.launch {
                            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                loginViewModel.action.collect { action ->
                                    when (action) {
                                        is LoginUiAction.LoginSuccess -> {
                                            mainNavController.navigate(MainNavScreen.BottomNavScreens.route) {
                                                popUpTo(mainNavController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                            }
                                        }

                                        is LoginUiAction.LoginError -> scope.launch {
                                            snackbarHostState.showSnackbar(action.message)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    MainNavHost(
                        mainNavController = mainNavController,
                        bottomBarNavController = bottomBarNavController,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}
