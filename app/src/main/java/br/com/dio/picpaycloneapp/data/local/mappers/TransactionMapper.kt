package br.com.dio.picpaycloneapp.data.local.mappers

import br.com.dio.picpaycloneapp.data.local.entities.TransactionEntity
import br.com.dio.picpaycloneapp.data.local.entities.TransactionWithUsersEntity
import br.com.dio.picpaycloneapp.domain.models.Transaction

fun Transaction.toEntity() : TransactionWithUsersEntity = TransactionWithUsersEntity(
    transaction = TransactionEntity(
        code = code,
        origin = origin.login,
        destination = destination.login,
        dateTime = dateTime,
        amount = amount,
        isCreditCard = isCreditCard
    ),
    origin = origin.toEntity(),
    destination = destination.toEntity()
)

fun TransactionWithUsersEntity.toModel(): Transaction = Transaction(
    code = transaction.code,
    origin = origin.toModel(),
    destination = destination.toModel(),
    dateTime = transaction.dateTime,
    amount = transaction.amount,
    isCreditCard = transaction.isCreditCard
)

fun List<Transaction>.toEntity() = this.map { transaction -> transaction.toEntity() }

fun List<TransactionWithUsersEntity>.toModel() = this.map { transactionWithUsersEntity ->
    transactionWithUsersEntity.toModel()
}
