package br.com.dio.picpaycloneapp.ui.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.picpaycloneapp.domain.models.Balance
import br.com.dio.picpaycloneapp.domain.models.BannerCard
import br.com.dio.picpaycloneapp.domain.models.CreditCard
import br.com.dio.picpaycloneapp.domain.models.Transaction
import br.com.dio.picpaycloneapp.domain.models.User
import br.com.dio.picpaycloneapp.domain.exceptions.ValidationException
import br.com.dio.picpaycloneapp.domain.repositories.TransactionRepository
import br.com.dio.picpaycloneapp.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
class TransactionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionsRepository: TransactionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionUiState())
    val state: StateFlow<TransactionUiState> = _state

    private val _action = MutableSharedFlow<TransactionUiAction>()
    val action: SharedFlow<TransactionUiAction> = _action

    fun fetchLoggedUserBalance(login: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val balance = userRepository.getUserBalance(login)
            _state.update { currentState ->
                currentState.copy(balance = balance, isUserBalance = true)
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

    fun transfer(originUser: User, destinationUser: User, saveCreditCard: Boolean = true) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }

            val transaction = if (state.value.paymentType == PaymentType.CREDIT_CARD) {
                createTransactionWithCreditCard(originUser, destinationUser, saveCreditCard)
            } else {
                if (state.value.amount.isEmpty()) {
                    throw ValidationException("Valor da transação deve ser positivo.")
                }
                if (_state.value.amount.toDouble() > originUser.balance) {
                    throw ValidationException("Saldo insuficiente.")
                }
                createTransaction(originUser, destinationUser)
            }

            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    transactionsRepository.makeTransaction(transaction)
                }.onFailure { exception ->
                    when (exception) {
                        is RetrofitHttpException -> {
                            when (exception.code()) {
                                HttpURLConnection.HTTP_BAD_REQUEST -> {
                                    sendAction(TransactionUiAction
                                        .TransactionError("Dados inválidos"))
                                }

                                HttpURLConnection.HTTP_NOT_FOUND -> {
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

                    _state.update { currentState ->
                        currentState.copy(isLoading = false)
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

                    cleanForm()
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

            _state.update { currentState ->
                currentState.copy(isLoading = false)
            }
        }
    }

    private fun createTransactionWithCreditCard(
        originUser: User,
        destinationUser: User,
        saveCreditCard: Boolean
    ): Transaction {
        val transaction = createTransaction(originUser, destinationUser)
        return transaction.copy(creditCard = createCreditCard(originUser, saveCreditCard))
    }

    private fun createTransaction(originUser: User, destinationUser: User): Transaction {
        validateTransaction()
        return Transaction(
            code = Transaction.generateHash(),
            origin = originUser,
            destination = destinationUser,
            dateTime = OffsetDateTime.now().toString(),
            isCreditCard = state.value.paymentType == PaymentType.CREDIT_CARD,
            amount = state.value.amount.toDouble() / 100,
            creditCard = null
        )
    }

    private fun validateTransaction() {
        if (state.value.amount.isEmpty() || state.value.amount.toDouble() <= 0.00) {
            throw ValidationException("Valor da transação deve ser positivo.")
        }
    }

    private fun createCreditCard(originUser: User, save: Boolean): CreditCard {
        validateCreditCard()
        return CreditCard(
            banner = BannerCard.VISA,
            number = state.value.cardNumber,
            holderName = state.value.holderName,
            expirationDate = state.value.expirationDate,
            securityCode = state.value.securityCode,
            user = originUser,
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

    private fun cleanForm() {
        _state.update { currentState ->
            currentState.copy(
                isLoading = false,
                amount = "",
                cardNumber = "",
                holderName = "",
                expirationDate = "",
                securityCode = "",
            )
        }
    }

    private fun sendAction(transactionUiAction: TransactionUiAction) {
        viewModelScope.launch(Dispatchers.IO) {
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
    val balance: Balance = Balance(),
    val isUserBalance: Boolean = false
)

sealed interface TransactionUiAction {
    data class TransactionSuccess(val message: String) : TransactionUiAction
    data class TransactionError(val message: String) : TransactionUiAction
}
