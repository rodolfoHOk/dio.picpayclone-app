package br.com.dio.picpaycloneapp.data.repositories

import br.com.dio.picpaycloneapp.domain.models.Balance
import br.com.dio.picpaycloneapp.domain.models.User
import br.com.dio.picpaycloneapp.domain.repositories.UserRepository
import br.com.dio.picpaycloneapp.data.remote.services.ApiService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository {

    override suspend fun getUserBalance(login: String) : Balance {
        return apiService.getUserBalance(login)
    }

    override suspend fun getUserByLogin(login: String): User {
        return apiService.getUserByLogin(login)
    }

    override suspend fun getUserContacts(login: String): List<User> {
        return apiService.getUserContacts(login)
    }

}
