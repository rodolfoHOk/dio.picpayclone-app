package br.com.dio.picpaycloneapp.data.repositories

import br.com.dio.picpaycloneapp.domain.models.Login
import br.com.dio.picpaycloneapp.domain.models.Token
import br.com.dio.picpaycloneapp.domain.repositories.AuthenticationRepository
import br.com.dio.picpaycloneapp.data.remote.services.ApiService
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthenticationRepository {

    override suspend fun authenticate(login: Login) : Token {
        return apiService.authenticate(login)
    }

}
