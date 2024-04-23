package br.com.dio.picpaycloneapp.domain.repositories

import br.com.dio.picpaycloneapp.domain.models.Balance
import br.com.dio.picpaycloneapp.domain.models.User

interface UserRepository {

    suspend fun getUserBalance(login: String) : Balance
    suspend fun getUserByLogin(login: String) : User
    suspend fun getUserContacts(login: String): List<User>

}
