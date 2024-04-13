package br.com.dio.picpaycloneapp.ui.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.picpaycloneapp.data.Balance
import br.com.dio.picpaycloneapp.data.BannerCard
import br.com.dio.picpaycloneapp.data.CreditCard
import br.com.dio.picpaycloneapp.data.LoggedUser
import br.com.dio.picpaycloneapp.data.Transaction
import br.com.dio.picpaycloneapp.data.User
import br.com.dio.picpaycloneapp.exceptions.ValidationException
import br.com.dio.picpaycloneapp.services.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.time.OffsetDateTime
import retrofit2.HttpException as RetrofitHttpException
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _state = MutableStateFlow(TransactionUiState())
    val state: StateFlow<TransactionUiState> = _state

    private val _action = MutableSharedFlow<TransactionUiAction>()
    val action: SharedFlow<TransactionUiAction> = _action

    init {
        fetchLoggedUserBalance()
    }

    private fun fetchLoggedUserBalance() = viewModelScope.launch {
        try {
            val login = LoggedUser.user.login
            val balance = apiService.getUserBalance(login)
            _state.update { currentState ->
                currentState.copy(balance = balance)
            }
        } catch (exception: Exception) {
            sendAction(
                TransactionUiAction
                    .TransactionError("Ops!, erro ao tentar buscar saldo do usuário logado.")
            )
        }
    }

    fun updateAmount(amount: String) {
        if (amount.length <= 12) {
            _state.update { currentState ->
                currentState.copy(amount = amount)
            }
        }
    }

    fun updatePaymentType(paymentType: PaymentType) {
        _state.update { currentState ->
            currentState.copy(paymentType = paymentType)
        }
    }

    fun updateCardNumber(cardNumber: String) {
        if (cardNumber.length <= 16) {
            _state.update { currentState ->
                currentState.copy(cardNumber = cardNumber)
            }
        }
    }

    fun updateHolderName(holderName: String) {
        if (holderName.length <= 255) {
            _state.update { currentState ->
                currentState.copy(holderName = holderName)
            }
        }
    }

    fun updateExpirationDate(expirationDate: String) {
        if (expirationDate.length <= 6) {
            _state.update { currentState ->
                currentState.copy(expirationDate = expirationDate)
            }
        }
    }

    fun updateSecurityCode(securityCode: String) {
        if (securityCode.length <= 3) {
            _state.update { currentState ->
                currentState.copy(securityCode = securityCode)
            }
        }
    }

    fun transfer(destinationUser: User, saveCreditCard: Boolean = true) {
        try {
            val transaction = if (state.value.paymentType == PaymentType.CREDIT_CARD) {
                createTransactionWithCreditCard(destinationUser, saveCreditCard)
            } else {
                if (state.value.amount.length == 0) {
                    throw ValidationException("Valor da transação deve ser positivo.")
                }
                if (_state.value.amount.toDouble() > LoggedUser.user.balance) {
                    throw ValidationException("Saldo insuficiente.")
                }
                createTransaction(destinationUser)
            }

            viewModelScope.launch {
                runCatching {
                    apiService.makeTransaction(transaction)
                }.onFailure { exception ->
                    when (exception) {
                        is RetrofitHttpException -> {
                            when (exception.code()) {
                                HttpURLConnection.HTTP_BAD_REQUEST -> {
                                    sendAction(TransactionUiAction
                                        .TransactionError("Dados inválidos"))
                                }

                                HttpURLConnection.HTTP_NOT_FOUND -> {
                                    val message = exception.response()?.message().toString()
                                    sendAction(TransactionUiAction
                                        .TransactionError("Dados inválidos"))
                                }

                                else -> {
                                    sendAction(
                                        TransactionUiAction
                                            .TransactionError("Erro ao realizar transação.")
                                    )
                                }
                            }
                        }

                        else -> throw exception
                    }
                }.onSuccess { transaction ->
                    sendAction(
                        TransactionUiAction
                            .TransactionSuccess(
                                "Transação realizada com sucesso. Código da transação: ${
                                    transaction.code
                                }"
                            )
                    )
                }
            }
        } catch (exception: Throwable) {
            when (exception) {
                is ValidationException -> {
                    sendAction(
                        TransactionUiAction.TransactionError(
                            exception.message ?: "Erro ao realizar transação."
                        )
                    )
                }

                else -> {
                    sendAction(TransactionUiAction.TransactionError("Erro ao realizar transação."))
                }
            }
        }
    }

    private fun createTransactionWithCreditCard(
        destinationUser: User,
        saveCreditCard: Boolean
    ): Transaction {
        val transaction = createTransaction(destinationUser)
        return transaction.copy(creditCard = createCreditCard(saveCreditCard))
    }

    private fun createTransaction(destinationUser: User): Transaction {
        validateTransaction()
        return Transaction(
            code = Transaction.generateHash(),
            origin = LoggedUser.user,
            destination = destinationUser,
            dateTime = OffsetDateTime.now().toString(),
            isCreditCard = state.value.paymentType == PaymentType.CREDIT_CARD,
            amount = state.value.amount.toDouble()
        )
    }

    private fun validateTransaction() {
        if (state.value.amount.length == 0 || state.value.amount.toDouble() <= 0.00) {
            throw ValidationException("Valor da transação deve ser positivo.")
        }
    }

    private fun createCreditCard(save: Boolean): CreditCard {
        validateCreditCard()
        return CreditCard(
            banner = BannerCard.VISA,
            number = state.value.cardNumber,
            holderName = state.value.holderName,
            expirationDate = state.value.expirationDate,
            securityCode = state.value.securityCode,
            user = LoggedUser.user,
            tokenNumber = "",
            isSaved = save
        )
    }

    private fun validateCreditCard() {
        if (state.value.cardNumber.length != 16) {
            throw ValidationException("Número do cartão deve ter 16 digitos.")
        }
        if (state.value.holderName.length < 3) {
            throw ValidationException("Nome do titular deve ter ao menos 3 caracteres.")
        }
        if (state.value.expirationDate.length != 6) {
            throw ValidationException("Vencimento deve ter dois digito do mês e mais quatro do ano.")
        }
        if (state.value.securityCode.length != 3) {
            throw ValidationException("CVC deve ter três digitos.")
        }
    }

    private fun sendAction(transactionUiAction: TransactionUiAction) {
        viewModelScope.launch {
            _action.emit(transactionUiAction)
        }
    }
}

enum class PaymentType(val description: String) {
    CREDIT_CARD("Cartão de Crédito"),
    BALANCE("Saldo")
}

data class TransactionUiState(
    val isLoading: Boolean = false,
    var amount: String = "",
    var paymentType: PaymentType = PaymentType.CREDIT_CARD,
    val cardNumber: String = "",
    val holderName: String = "",
    val expirationDate: String = "",
    val securityCode: String = "",
    val balance: Balance = Balance()
)

sealed interface TransactionUiAction {
    data class TransactionSuccess(val message: String) : TransactionUiAction
    data class TransactionError(val message: String) : TransactionUiAction
}
