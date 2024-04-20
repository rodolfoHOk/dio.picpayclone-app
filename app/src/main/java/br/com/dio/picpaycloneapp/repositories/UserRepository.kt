package br.com.dio.picpaycloneapp.repositories

import br.com.dio.picpaycloneapp.models.Balance
import br.com.dio.picpaycloneapp.models.User

interface UserRepository {

    suspend fun getUserBalance(login: String) : Balance
    suspend fun getUserByLogin(username: String) : User
    suspend fun getUserContacts(login: String): List<User>

}
