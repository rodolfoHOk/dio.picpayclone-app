package br.com.dio.picpaycloneapp.repositories

import androidx.paging.PagingData
import br.com.dio.picpaycloneapp.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getTransactions(login: String): Flow<PagingData<Transaction>>

    suspend fun makeTransaction(transaction: Transaction): Transaction

}

