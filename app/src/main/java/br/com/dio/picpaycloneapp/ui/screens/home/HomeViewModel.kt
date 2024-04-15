package br.com.dio.picpaycloneapp.ui.screens.home

import androidx.lifecycle.ViewModel
import br.com.dio.picpaycloneapp.data.Balance
import br.com.dio.picpaycloneapp.data.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state
}

data class HomeUiState(
    val isLoadingBalance: Boolean = false,
    val isLoadingTransactions: Boolean = false,
    val balance: Balance = Balance(),
    val transactions: List<Transaction> = listOf()
)
