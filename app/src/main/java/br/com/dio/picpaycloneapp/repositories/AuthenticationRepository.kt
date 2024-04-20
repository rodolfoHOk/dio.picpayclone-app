package br.com.dio.picpaycloneapp.repositories

import br.com.dio.picpaycloneapp.models.Login
import br.com.dio.picpaycloneapp.models.Token

interface AuthenticationRepository {

    suspend fun authenticate(login: Login) : Token

}
