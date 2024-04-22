package br.com.dio.picpaycloneapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "transaction_code")
    val transactionCode: String,
    val login: String,
    val prevKey: Int?,
    val nextKey: Int?
)
