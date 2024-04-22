package br.com.dio.picpaycloneapp.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.dio.picpaycloneapp.domain.models.Transaction
import br.com.dio.picpaycloneapp.data.remote.services.ApiService
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class TransactionsPagingSource(
    private val apiService: ApiService,
    private val login: String
) : PagingSource<Int, Transaction>() {

    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val page = params.key ?: API_STARTING_PAGE_INDEX
            val size = params.loadSize
//            throw IOException("Test") // for test
            val pageTransaction = apiService.getTransactions(login, page, size)
            delay(1000) // for test
            LoadResult.Page(
                data = pageTransaction.content,
                prevKey = if (page <= API_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page >= pageTransaction.totalPages - 1) null
                else page + (size / NETWORK_PAGE_SIZE)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val API_STARTING_PAGE_INDEX = 0
        const val NETWORK_PAGE_SIZE = 5
    }

}
