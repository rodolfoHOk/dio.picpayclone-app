package br.com.dio.picpaycloneapp.repositories.impl

import br.com.dio.picpaycloneapp.models.Balance
import br.com.dio.picpaycloneapp.models.User
import br.com.dio.picpaycloneapp.repositories.UserRepository
import br.com.dio.picpaycloneapp.services.ApiService
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
