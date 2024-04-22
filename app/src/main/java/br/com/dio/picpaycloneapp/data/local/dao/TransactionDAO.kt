package br.com.dio.picpaycloneapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.com.dio.picpaycloneapp.data.local.entities.TransactionEntity
import br.com.dio.picpaycloneapp.data.local.entities.TransactionWithUsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDAO {

    @Transaction
    @Query("SELECT * FROM transactions")
    fun getAll(): Flow<List<TransactionWithUsersEntity>>

    @Transaction
    @Query("SELECT * FROM transactions WHERE origin = :login")
    fun getAllByUserLogin(login: String): PagingSource<Int, TransactionWithUsersEntity>

    @Transaction
    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getById(id: Int): Flow<TransactionWithUsersEntity>

    @Transaction
    @Query("SELECT * FROM transactions WHERE code = :code")
    fun getByCode(code: String): Flow<TransactionWithUsersEntity>

    @Query("Delete FROM transactions WHERE origin = :login")
    fun clearByUserLogin(login: String)

    @Query("Delete FROM transactions")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetAll(transactions: List<TransactionEntity>)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Update
    suspend fun update(transaction: TransactionEntity)

}
