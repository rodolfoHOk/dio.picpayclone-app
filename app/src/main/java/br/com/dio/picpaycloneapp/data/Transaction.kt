package br.com.dio.picpaycloneapp.data

import java.util.UUID

data class Transaction(
    val code: String = "",
    val origin: User = User(),
    val destination: User = User(),
    val dateTime: String = "",
    val amount: Double = 0.0,
    val creditCard: CreditCard = CreditCard(),
    val isCreditCard: Boolean = false
) {
    companion object {
        fun generateHash(): String = UUID.randomUUID().toString()
    }
}
