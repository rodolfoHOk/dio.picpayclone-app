package br.com.dio.picpaycloneapp.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import br.com.dio.picpaycloneapp.data.local.AppDatabase
import br.com.dio.picpaycloneapp.data.local.mappers.toModel
import br.com.dio.picpaycloneapp.domain.models.Transaction
import br.com.dio.picpaycloneapp.domain.repositories.TransactionRepository
import br.com.dio.picpaycloneapp.data.remote.services.ApiService
import br.com.dio.picpaycloneapp.data.sources.TransactionsMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : TransactionRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getTransactions(login: String): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
//                initialLoadSize = NETWORK_PAGE_SIZE, // for test
            ),
            pagingSourceFactory = { appDatabase.transitionDAO().getAllByUserLogin(login) },
            remoteMediator = TransactionsMediator(login, apiService, appDatabase)
        ).flow.map { pagingData ->
            pagingData.map { transactionEntity ->
                transactionEntity.toModel()
            }
        }
    }

    override suspend fun makeTransaction(transaction: Transaction) : Transaction {
        return apiService.makeTransaction(transaction)
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 5
    }

}
