package br.com.dio.picpaycloneapp.models

data class User(
    val login: String = "",
    val password: String = "",
    val email: String = "",
    val completeName: String = "",
    val cpf: String = "",
    val birthday: String = "",
    val phoneNumber: String = "",
    var balance: Double = 0.0
)
