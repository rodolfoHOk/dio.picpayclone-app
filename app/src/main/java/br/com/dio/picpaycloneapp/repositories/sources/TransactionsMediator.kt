package br.com.dio.picpaycloneapp.repositories.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import br.com.dio.picpaycloneapp.data.AppDatabase
import br.com.dio.picpaycloneapp.data.mappers.toEntity
import br.com.dio.picpaycloneapp.models.PageTransaction
import br.com.dio.picpaycloneapp.services.ApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TransactionsMediator(
    private val login: String,
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, PageTransaction>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PageTransaction>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val lastItem = state.lastItemOrNull()
                lastItem?.number ?: API_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null ||
                    lastItem.number == API_STARTING_PAGE_INDEX
                ) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                lastItem.number - 1
            }

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null ||
                    lastItem.content.isEmpty() ||
                    lastItem.number >= lastItem.totalPages - 1
                ) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                lastItem.number + 1
            }
        }

        return try {
            val apiResponse = apiService.getTransactions(login, page, NETWORK_PAGE_SIZE)
            val transactions = apiResponse.content
            val endOfPaginationReached = transactions.isEmpty()
            appDatabase.withTransaction {
                appDatabase.transitionDAO().insetAll(transactions.toEntity())
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    companion object {
        const val API_STARTING_PAGE_INDEX = 0
        const val NETWORK_PAGE_SIZE = 5
    }

}
