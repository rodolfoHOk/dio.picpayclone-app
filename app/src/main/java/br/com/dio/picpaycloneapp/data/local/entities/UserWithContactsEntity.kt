package br.com.dio.picpaycloneapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithContactsEntity(

    @Embedded
    val user : UserEntity,

    @Relation(
        parentColumn = "userLogin",
        entityColumn = "contactLogin",
        associateBy = Junction(UserContactCrossRef::class)
    )
    val contacts : List<UserEntity>
)
