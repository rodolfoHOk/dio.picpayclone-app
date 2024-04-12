package br.com.dio.picpaycloneapp.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.picpaycloneapp.data.LoggedUser
import br.com.dio.picpaycloneapp.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState(isLoading = false))
    val state: StateFlow<LoginUiState> get() = _state

    private val _action = MutableSharedFlow<LoginUiAction>()
    val action: SharedFlow<LoginUiAction> = _action

    var username by mutableStateOf("joaovf")
        private set

    var password by mutableStateOf("")
        private set

    fun updateUsername(username: String) {
        this.username = username
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun login() {
        _state.update { currentState ->
            currentState.copy(isLoading = true)
        }

        if (username == "joaovf") {
            LoggedUser.user = User(
                login = username,
                completeName = "João Vitor Freitas",
                balance = 500.00,
            )
            sendAction(LoginUiAction.LoginSuccess("Login efetuado com sucesso!"))
            return
        }

        _state.update { currentState ->
            currentState.copy(isLoading = false)
        }
        sendAction(LoginUiAction.LoginError("Erro ao efetuar login!"))
    }

    private fun sendAction(loginUiAction: LoginUiAction) {
        viewModelScope.launch {
            _action.emit(loginUiAction)
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean
)

sealed interface LoginUiAction {
    data class LoginSuccess(val message: String) : LoginUiAction
    data class LoginError(val message: String) : LoginUiAction
}
