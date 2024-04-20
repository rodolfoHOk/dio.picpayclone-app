package br.com.dio.picpaycloneapp.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["code"], unique = true)
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val code: String = "",
    val origin: String = "",
    val destination: String = "",
    val dateTime: String = "",
    val amount: Double = 0.0,
    val isCreditCard: Boolean = false
)
