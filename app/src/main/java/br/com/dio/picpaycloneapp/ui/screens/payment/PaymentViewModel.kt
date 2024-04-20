package br.com.dio.picpaycloneapp.ui.screens.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.picpaycloneapp.models.User
import br.com.dio.picpaycloneapp.services.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _state = MutableStateFlow(PaymentUiState())
    val state: StateFlow<PaymentUiState> = _state

    private val _action = MutableSharedFlow<PaymentUiAction>()
    val action: SharedFlow<PaymentUiAction> = _action

    fun fetchUserContacts(login: String) = viewModelScope.launch {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }

            val userContacts = apiService.getUserContacts(login)

            _state.update { currentState ->
                currentState.copy(contacts = userContacts, isUserContacts = true)
            }
        } catch (exception: Exception) {
            sendAction(PaymentUiAction.ContactsError("Ops!, erro ao tentar buscar contatos."))
        } finally {
            _state.update { currentState ->
                currentState.copy(isLoading = false)
            }
        }
    }

    private fun sendAction(paymentUiAction: PaymentUiAction) {
        viewModelScope.launch {
            _action.emit(paymentUiAction)
        }
    }
}

data class PaymentUiState(
    val contacts: List<User> = listOf(),
    val isUserContacts: Boolean = false,
    val isLoading: Boolean = false
)

sealed interface PaymentUiAction {
    data class ContactsError(val message: String) : PaymentUiAction
}
