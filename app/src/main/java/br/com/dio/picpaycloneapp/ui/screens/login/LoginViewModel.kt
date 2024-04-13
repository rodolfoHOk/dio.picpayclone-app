package br.com.dio.picpaycloneapp.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.picpaycloneapp.data.LoggedUser
import br.com.dio.picpaycloneapp.services.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import retrofit2.HttpException as RetrofitHttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> get() = _state

    private val _action = MutableSharedFlow<LoginUiAction>()
    val action: SharedFlow<LoginUiAction> = _action

    fun updateUsername(username: String) {
        if (username.length <= 255) {
            _state.update { currentState ->
                currentState.copy(username = username)
            }
        }
    }

    fun updatePassword(password: String) {
        if (password.length <= 255) {
            _state.update { currentState ->
                currentState.copy(password = password)
            }
        }
    }

    fun login() {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }

            viewModelScope.launch {
                runCatching {
                    apiService.getUserByLogin(state.value.username)
                }.onFailure { exception ->
                    when (exception) {
                        is RetrofitHttpException -> {
                            when (exception.code()) {
                                HttpURLConnection.HTTP_NOT_FOUND -> {
                                    sendAction(LoginUiAction.LoginError("Credenciais inválidas"))
                                }
                                else -> sendAction(LoginUiAction.LoginError("Erro ao efetuar login!"))
                            }
                        }
                        else -> throw exception
                    }
                }.onSuccess { user ->
                    LoggedUser.user = user
                    sendAction(LoginUiAction.LoginSuccess("Login efetuado com sucesso"))
                }
            }
        } catch (exception: Exception) {
            sendAction(LoginUiAction.LoginError("Erro ao efetuar login!"))
        } finally {
            _state.update { currentState ->
                currentState.copy(isLoading = false)
            }
        }
    }

    private fun sendAction(loginUiAction: LoginUiAction) {
        viewModelScope.launch {
            _action.emit(loginUiAction)
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    var username: String = "joaovf",
    var password: String = ""
)

sealed interface LoginUiAction {
    data class LoginSuccess(val message: String) : LoginUiAction
    data class LoginError(val message: String) : LoginUiAction
}
