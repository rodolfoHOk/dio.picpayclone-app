package br.com.dio.picpaycloneapp.repositories.impl

import br.com.dio.picpaycloneapp.models.Login
import br.com.dio.picpaycloneapp.models.Token
import br.com.dio.picpaycloneapp.repositories.AuthenticationRepository
import br.com.dio.picpaycloneapp.services.ApiService
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthenticationRepository {

    override suspend fun authenticate(login: Login) : Token {
        return apiService.authenticate(login)
    }

}
