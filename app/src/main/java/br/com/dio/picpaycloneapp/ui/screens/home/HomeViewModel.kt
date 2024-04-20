package br.com.dio.picpaycloneapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.dio.picpaycloneapp.models.Balance
import br.com.dio.picpaycloneapp.models.Transaction
import br.com.dio.picpaycloneapp.repositories.TransactionsRepository
import br.com.dio.picpaycloneapp.services.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val transactionsRepository: TransactionsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state

    private val _action = MutableSharedFlow<HomeUiAction>()
    val action: SharedFlow<HomeUiAction> = _action

    fun fetchLoggerUserBalance(login: String) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoadingBalance = true)
            }

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

    fun getTransactions(login: String) : Flow<PagingData<Transaction>> {
        return transactionsRepository.getTransactions(login).cachedIn(viewModelScope)
    }

    private fun sendAction(action: HomeUiAction) {
        viewModelScope.launch {
            _action.emit(action)
        }
    }
}

data class HomeUiState(
    val isLoadingBalance: Boolean = false,
    val balance: Balance = Balance()
)

sealed interface HomeUiAction {

    data class BalanceError(val message: String) : HomeUiAction

    data class TransactionsError(val message: String) : HomeUiAction
}
