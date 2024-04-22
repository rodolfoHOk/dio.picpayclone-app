package br.com.dio.picpaycloneapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "users_contacts",
    primaryKeys = ["user_login", "contact_login"]
)
data class UserContactCrossRef(
    @ColumnInfo(name = "user_login")
    val userLogin: String,
    @ColumnInfo(name = "contact_login")
    var contactLogin: String
)
