package br.com.dio.picpaycloneapp.data.sources

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.dio.picpaycloneapp.data.models.Transaction
import br.com.dio.picpaycloneapp.services.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsRepository @Inject constructor(private val apiService: ApiService) {

    fun getTransactions(login: String): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
//                initialLoadSize = NETWORK_PAGE_SIZE,
            ),
            pagingSourceFactory = { TransactionsPagingSource(apiService, login) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

}
