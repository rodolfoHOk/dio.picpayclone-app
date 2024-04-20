package br.com.dio.picpaycloneapp.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["login"], unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val login: String = "",
    val email: String = "",
    val completeName: String = "",
    val cpf: String = "",
    val birthday: String = "",
    val phoneNumber: String = "",
    var balance: Double = 0.0
)
