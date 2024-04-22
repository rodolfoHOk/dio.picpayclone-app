package br.com.dio.picpaycloneapp.data.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import br.com.dio.picpaycloneapp.data.local.AppDatabase
import br.com.dio.picpaycloneapp.data.local.entities.RemoteKey
import br.com.dio.picpaycloneapp.data.local.entities.TransactionWithUsersEntity
import br.com.dio.picpaycloneapp.data.local.mappers.toEntity
import br.com.dio.picpaycloneapp.data.remote.services.ApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TransactionsMediator(
    private val login: String,
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, TransactionWithUsersEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TransactionWithUsersEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state, login)
                remoteKey?.nextKey?.minus(1) ?: API_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state, login)
                val prevKey = remoteKey?.prevKey ?: return MediatorResult
                    .Success(endOfPaginationReached = remoteKey != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state, login)
                val nextKey = remoteKey?.nextKey ?: return MediatorResult
                    .Success(endOfPaginationReached = remoteKey != null)
                nextKey
            }
        }

        return try {
            val apiResponse = apiService.getTransactions(login, page, state.config.pageSize)
            val transactions = apiResponse.content
            val endOfPaginationReached = transactions.isEmpty()
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.remoteKeyDAO().clearByUserLogin(login)
                    appDatabase.transitionDAO().clearByUserLogin(login)
                }
                val prevKey = if (page <= API_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = transactions.map { transaction ->
                    val transactionEntity = transaction.toEntity()
                    appDatabase.userDAO().insert(transactionEntity.origin)
                    appDatabase.userDAO().insert(transactionEntity.destination)
                    appDatabase.transitionDAO().insert(transactionEntity.transaction)
                    RemoteKey(
                        transactionCode = transaction.code,
                        login = login,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                appDatabase.remoteKeyDAO().insertAll(keys)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, TransactionWithUsersEntity>,
        login: String
    ): RemoteKey? {
        return state.pages.firstOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { transaction ->
            appDatabase.remoteKeyDAO().getRemoteKeyByLoginAndTransactionCode(
                login = login,
                transactionCode = transaction.transaction.code
            )
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, TransactionWithUsersEntity>,
        login: String
    ): RemoteKey? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { transaction ->
            appDatabase.remoteKeyDAO().getRemoteKeyByLoginAndTransactionCode(
                login = login,
                transactionCode = transaction.transaction.code
            )
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TransactionWithUsersEntity>,
        login: String
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.transaction?.code?.let { transactionCode ->
                appDatabase.remoteKeyDAO().getRemoteKeyByLoginAndTransactionCode(
                    login = login,
                    transactionCode = transactionCode
                )
            }
        }
    }

    companion object {
        const val API_STARTING_PAGE_INDEX = 0
    }

}
