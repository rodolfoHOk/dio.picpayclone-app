package br.com.dio.picpaycloneapp.data

object UserToken {

    lateinit var bearerToken: String

    fun hasToken(): Boolean = this::bearerToken.isInitialized

}
