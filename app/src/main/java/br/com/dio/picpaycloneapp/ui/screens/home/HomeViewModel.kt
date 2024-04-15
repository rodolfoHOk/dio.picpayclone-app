package br.com.dio.picpaycloneapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.picpaycloneapp.data.Balance
import br.com.dio.picpaycloneapp.data.LoggedUser
import br.com.dio.picpaycloneapp.data.PageTransaction
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
class HomeViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    private val _action = MutableSharedFlow<HomeUiAction>()
    val action: SharedFlow<HomeUiAction> = _action

    fun fetchLoggerUserBalance() {
        try {
            _state.update { currentState ->
                currentState.copy(isLoadingBalance = true)
            }

            val login = LoggedUser.user.login

            viewModelScope.launch {
                runCatching {
                    apiService.getUserBalance(login)
                }.onFailure { throwable ->
                    when (throwable) {
                        is RetrofitHttpException -> {
                            when (throwable.code()) {
                                HttpURLConnection.HTTP_NOT_FOUND -> {
                                    sendAction(
                                        HomeUiAction
                                            .BalanceError("User not found by informed login")
                                    )
                                }

                                HttpURLConnection.HTTP_BAD_REQUEST -> {
                                    sendAction(
                                        HomeUiAction.BalanceError("Dados informados inválidos")
                                    )
                                }

                                else -> sendAction(
                                    HomeUiAction
                                        .BalanceError("Ops!, erro ao tentar buscar saldo do usuário")
                                )
                            }
                        }

                        else -> sendAction(
                            HomeUiAction
                                .BalanceError("Ops!, erro ao tentar buscar saldo do usuário")
                        )
                    }
                }.onSuccess { balance ->
                    _state.update { currentState ->
                        currentState.copy(balance = balance)
                    }
                }
            }
        } catch (throwable: Throwable) {
            sendAction(
                HomeUiAction
                    .BalanceError("Ops!, erro ao tentar buscar saldo do usuário")
            )
        } finally {
            _state.update { currentState ->
                currentState.copy(isLoadingBalance = false)
            }
        }
    }

    fun fetchLoggerUserTransactions() {
        try {
            _state.update { currentState ->
                currentState.copy(isLoadingTransactions = true)
            }

            val login = LoggedUser.user.login

            viewModelScope.launch {
                runCatching {
                    apiService.getTransactions(login)
                }.onFailure { throwable ->
                    when (throwable) {
                        is RetrofitHttpException -> {
                            when (throwable.code()) {
                                HttpURLConnection.HTTP_NOT_FOUND -> {
                                    sendAction(
                                        HomeUiAction
                                            .TransactionsError("User not found by informed login")
                                    )
                                }

                                HttpURLConnection.HTTP_BAD_REQUEST -> {
                                    sendAction(
                                        HomeUiAction.TransactionsError("Dados informados inválidos")
                                    )
                                }

                                else -> sendAction(
                                    HomeUiAction
                                        .TransactionsError(
                                            "Ops!, erro ao tentar buscar transações do usuário"
                                        )
                                )
                            }
                        }

                        else -> sendAction(
                            HomeUiAction
                                .TransactionsError(
                                    "Ops!, erro ao tentar buscar transações do usuário"
                                )
                        )
                    }
                }.onSuccess { pageTransactions ->
                    _state.update { currentState ->
                        currentState.copy(pageTransactions = pageTransactions)
                    }
                }
            }
        } catch (throwable: Throwable) {
            sendAction(
                HomeUiAction
                    .TransactionsError("Ops!, erro ao tentar buscar transações do usuário")
            )
        } finally {
            _state.update { currentState ->
                currentState.copy(isLoadingTransactions = false)
            }
        }
    }

    private fun sendAction(action: HomeUiAction) {
        viewModelScope.launch {
            _action.emit(action)
        }
    }
}

data class HomeUiState(
    val isLoadingBalance: Boolean = false,
    val isLoadingTransactions: Boolean = false,
    val balance: Balance = Balance(),
    val pageTransactions: PageTransaction = PageTransaction()
)

sealed interface HomeUiAction {

    data class BalanceError(val message: String) : HomeUiAction

    data class TransactionsError(val message: String) : HomeUiAction
}
