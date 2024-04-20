package br.com.dio.picpaycloneapp.repositories.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.dio.picpaycloneapp.models.Transaction
import br.com.dio.picpaycloneapp.repositories.TransactionRepository
import br.com.dio.picpaycloneapp.repositories.sources.TransactionsPagingSource
import br.com.dio.picpaycloneapp.services.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TransactionRepository {

    override fun getTransactions(login: String): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = TransactionsPagingSource.NETWORK_PAGE_SIZE,
//                initialLoadSize = TransactionsPagingSource.NETWORK_PAGE_SIZE,
            ),
            pagingSourceFactory = { TransactionsPagingSource(apiService, login) }
        ).flow
    }

    override suspend fun makeTransaction(transaction: Transaction) : Transaction {
        return apiService.makeTransaction(transaction)
    }

}
