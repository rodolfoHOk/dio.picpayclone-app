package br.com.dio.picpaycloneapp.repositories

object UserToken {

    lateinit var bearerToken: String

    fun hasToken(): Boolean = this::bearerToken.isInitialized

}
