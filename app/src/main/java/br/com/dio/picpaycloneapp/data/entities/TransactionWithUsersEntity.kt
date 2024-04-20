package br.com.dio.picpaycloneapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithUsersEntity(

    @Embedded
    val transaction: TransactionEntity,

    @Relation(
        parentColumn = "origin",
        entityColumn = "login"
    )
    val origin: UserEntity,

    @Relation(
        parentColumn = "destination",
        entityColumn = "login"
    )
    val destination: UserEntity
)
