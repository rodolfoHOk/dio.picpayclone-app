package br.com.dio.picpaycloneapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import br.com.dio.picpaycloneapp.data.models.Transaction
import br.com.dio.picpaycloneapp.ui.LocalSnackbarHostState
import kotlinx.coroutines.flow.Flow

@Composable
fun TransactionList(transactionsFlow: Flow<PagingData<Transaction>>) {
    val lazyPageTransactions = transactionsFlow.collectAsLazyPagingItems()

    if (lazyPageTransactions.loadState.append is LoadState.Error ||
        lazyPageTransactions.loadState.prepend is LoadState.Error ||
        lazyPageTransactions.loadState.refresh is LoadState.Error
    ) {
        val snackbarHostState = LocalSnackbarHostState.current
        LaunchedEffect(Unit) {
            snackbarHostState
                .showSnackbar(
                    message = "Erro ao tentar buscar dados de transações",
                    actionLabel = "Fechar",
                    duration = SnackbarDuration.Short
                )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.99f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    count = lazyPageTransactions.itemCount,
                    key = lazyPageTransactions.itemKey { it.code }
                ) { index ->
                    val transaction = lazyPageTransactions[index]
                    if (transaction != null)
                        TransactionItem(transaction = transaction)
                    else
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        ) {
            if (lazyPageTransactions.loadState.append is LoadState.Loading ||
                lazyPageTransactions.loadState.prepend is LoadState.Loading ||
                lazyPageTransactions.loadState.refresh is LoadState.Loading
            ) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}
