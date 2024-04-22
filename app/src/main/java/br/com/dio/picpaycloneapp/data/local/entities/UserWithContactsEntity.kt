package br.com.dio.picpaycloneapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithContactsEntity(

    @Embedded
    val user: UserEntity,

    @Relation(
        parentColumn = "login",
        entityColumn = "login",
        associateBy = Junction(
            UserContactCrossRef::class,
            parentColumn = "user_login",
            entityColumn = "contact_login",
        )
    )
    val contacts: List<UserEntity>
)
