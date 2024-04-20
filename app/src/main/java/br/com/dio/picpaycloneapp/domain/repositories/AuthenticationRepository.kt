package br.com.dio.picpaycloneapp.domain.repositories

import br.com.dio.picpaycloneapp.domain.models.Login
import br.com.dio.picpaycloneapp.domain.models.Token

interface AuthenticationRepository {

    suspend fun authenticate(login: Login) : Token

}
