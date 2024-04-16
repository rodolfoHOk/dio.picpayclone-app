package br.com.dio.picpaycloneapp.services

import br.com.dio.picpaycloneapp.data.models.Balance
import br.com.dio.picpaycloneapp.data.models.PageTransaction
import br.com.dio.picpaycloneapp.data.models.Transaction
import br.com.dio.picpaycloneapp.data.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/users/{login}")
    suspend fun getUserByLogin(@Path("login") login: String) : User

    @GET("/users/contacts")
    suspend fun getUserContacts(@Query("login") login: String) : List<User>

    @GET("/users/{login}/balance")
    suspend fun getUserBalance(@Path("login") login: String) : Balance

    @POST("/transactions")
    suspend fun makeTransaction(@Body transaction: Transaction): Transaction

    @GET("/transactions")
    suspend fun getTransactions(
        @Query("login") login: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): PageTransaction
}
