package br.com.dio.picpaycloneapp.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["userLogin", "contactLogin"])
data class UserContactCrossRef(
    val userLogin: String,
    var contactLogin: String
)
