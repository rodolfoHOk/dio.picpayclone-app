package br.com.dio.picpaycloneapp.data

object LoggedUser {

    lateinit var user: User

    fun isLoggedUser() = this::user.isInitialized

    fun isNotLoggedUser() = !isLoggedUser()
}
