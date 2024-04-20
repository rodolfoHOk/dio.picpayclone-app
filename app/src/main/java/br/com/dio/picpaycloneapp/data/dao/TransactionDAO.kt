package br.com.dio.picpaycloneapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.dio.picpaycloneapp.data.entities.TransactionWithUsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDAO {

    @Query("SELECT * FROM transactions")
    fun getAll(): Flow<List<TransactionWithUsersEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getById(id: Int): Flow<TransactionWithUsersEntity>

    @Query("SELECT * FROM transactions WHERE code = :code")
    fun getByCode(code: String): Flow<TransactionWithUsersEntity>

    @Query("Delete FROM transactions")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionWithUsersEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetAll(transactions: List<TransactionWithUsersEntity>)

    @Delete
    suspend fun delete(transaction: TransactionWithUsersEntity)

    @Update
    suspend fun update(transaction: TransactionWithUsersEntity)

}
