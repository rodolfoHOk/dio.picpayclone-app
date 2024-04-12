package br.com.dio.picpaycloneapp.ui.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(TransactionUiState())
    val state: StateFlow<TransactionUiState> = _state

    private val _action = MutableSharedFlow<TransactionUiAction>()
    val action: SharedFlow<TransactionUiAction> = _action

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
    val securityCode: String = ""
)

sealed interface TransactionUiAction {
    data class TransactionSuccess(val message: String) : TransactionUiAction
    data class TransactionError(val message: String) : TransactionUiAction
}
