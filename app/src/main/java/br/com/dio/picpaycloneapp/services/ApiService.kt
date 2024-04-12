package br.com.dio.picpaycloneapp.services

import br.com.dio.picpaycloneapp.data.User
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/users/contacts")
    suspend fun getUserContacts(@Query("login") login: String) : List<User>
}
