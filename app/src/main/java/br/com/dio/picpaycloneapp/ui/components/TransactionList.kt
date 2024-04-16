package br.com.dio.picpaycloneapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import br.com.dio.picpaycloneapp.data.models.Transaction
import kotlinx.coroutines.flow.Flow

@Composable
fun TransactionList(transactionsFlow: Flow<PagingData<Transaction>>) {
    val lazyPageTransactions = transactionsFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.padding(16.dp),
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
