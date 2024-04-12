package br.com.dio.picpaycloneapp.ui.screens.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.picpaycloneapp.data.LoggedUser
import br.com.dio.picpaycloneapp.data.User
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

    init {
        fetchUserContacts()
    }

    private fun fetchUserContacts() = viewModelScope.launch {
        try {
            val login = LoggedUser.user.login
            val userContacts = apiService.getUserContacts(login)
            _state.update { currentState ->
                currentState.copy(contacts = userContacts)
            }
        } catch (exception: Exception) {
            sendAction(PaymentUiAction.ContactsError("Ops!, erro ao tentar buscar contatos."))
        }
    }

    private fun sendAction(paymentUiAction: PaymentUiAction) {
        viewModelScope.launch {
            _action.emit(paymentUiAction)
        }
    }
}

data class PaymentUiState(
    val contacts: List<User> = listOf()
)

sealed interface PaymentUiAction {
    data class ContactsError(val message: String) : PaymentUiAction
}
