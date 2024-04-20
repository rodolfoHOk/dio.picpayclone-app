package br.com.dio.picpaycloneapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithUsersEntity(

    @Embedded
    val transaction: TransactionEntity,

    @Relation(
        parentColumn = "login",
        entityColumn = "origin"
    )
    val origin: UserEntity,

    @Relation(
        parentColumn = "login",
        entityColumn = "destination"
    )
    val destination: UserEntity
)
