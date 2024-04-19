package br.com.dio.picpaycloneapp.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.picpaycloneapp.data.models.Login
import br.com.dio.picpaycloneapp.data.models.User
import br.com.dio.picpaycloneapp.data.UserToken
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

            if (isValidLoginForm()) {
                viewModelScope.launch {
                    getAccessTokenAndLoggedUser()
                }
            } else {
                // TODO field validation errors
            }
        } catch (exception: Exception) {
            sendAction(LoginUiAction.LoginError("Erro ao efetuar login!"))
        } finally {
            logout()

            _state.update { currentState ->
                currentState.copy(isLoading = false)
            }
        }
    }

    fun logout() {
        _state.update { currentState ->
            currentState.copy(loggedUser = null, isLoggedUser = false)
        }
    }

    private fun sendAction(loginUiAction: LoginUiAction) = viewModelScope.launch {
        _action.emit(loginUiAction)
    }

    private fun isValidLoginForm(): Boolean {
        // TODO validate login form fields
        return true
    }

    private suspend fun getAccessTokenAndLoggedUser() = runCatching {
        apiService.authenticate(Login(state.value.username, state.value.password))
    }.onFailure { throwable ->
        when (throwable) {
            is RetrofitHttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> {
                        sendAction(LoginUiAction.LoginError("Dados inválidos."))
                    }

                    else -> sendAction(LoginUiAction.LoginError("Erro ao efetuar login."))
                }
            }

            else -> throw throwable
        }
    }.onSuccess { token ->
        UserToken.bearerToken = "${token.type} ${token.token}"
        getUserByUsername()
    }

    private suspend fun getUserByUsername() = runCatching {
        apiService.getUserByLogin(state.value.username)
    }.onFailure { throwable ->
        when (throwable) {
            is RetrofitHttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> {
                        sendAction(LoginUiAction.LoginError("Dados inválidos."))
                    }

                    HttpURLConnection.HTTP_NOT_FOUND -> {
                        sendAction(LoginUiAction.LoginError("Usuário não encontrado."))
                    }

                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        sendAction(
                            LoginUiAction
                                .LoginError("Usuário sem autorização, faça o login.")
                        )
                    }

                    HttpURLConnection.HTTP_FORBIDDEN -> {
                        sendAction(
                            LoginUiAction
                                .LoginError(
                                    "Usuário sem permissão de acesso, faça o login novamente."
                                )
                        )
                    }

                    else -> sendAction(LoginUiAction.LoginError("Erro ao buscar usuário logado."))
                }
            }

            else -> throw throwable
        }
    }.onSuccess { user ->
        _state.update { currentState ->
            currentState.copy(loggedUser = user, isLoggedUser = true)
        }
        sendAction(LoginUiAction.LoginSuccess("Login efetuado com sucesso."))
    }

}

data class LoginUiState(
    val isLoading: Boolean = false,
    val username: String = "joaovf",
    val password: String = "",
    val loggedUser: User? = null,
    val isLoggedUser: Boolean = false
)

sealed interface LoginUiAction {
    data class LoginSuccess(val message: String) : LoginUiAction
    data class LoginError(val message: String) : LoginUiAction
}
