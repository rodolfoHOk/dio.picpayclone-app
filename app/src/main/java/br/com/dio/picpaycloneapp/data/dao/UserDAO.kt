package br.com.dio.picpaycloneapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.dio.picpaycloneapp.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getById(id: Int): Flow<UserEntity>

    @Query("SELECT * FROM users WHERE login = :login")
    fun getByLogin(login: String): Flow<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: UserEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(users: List<UserEntity>)

    @Delete
    suspend fun delete(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

}
