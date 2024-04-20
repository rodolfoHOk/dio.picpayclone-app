package br.com.dio.picpaycloneapp.data.local.entities

import androidx.room.Entity

@Entity(tableName = "users_contacts", primaryKeys = ["userLogin", "contactLogin"])
data class UserContactCrossRef(
    val userLogin: String,
    var contactLogin: String
)
