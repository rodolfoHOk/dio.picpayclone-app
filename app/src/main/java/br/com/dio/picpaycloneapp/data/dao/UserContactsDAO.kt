package br.com.dio.picpaycloneapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dio.picpaycloneapp.data.entities.UserWithContactsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserContactsDAO {

    @Query("SELECT * FROM users_contacts WHERE userLogin = :userLogin")
    fun getUserContactsByUserLogin(userLogin: String) : Flow<UserWithContactsEntity>

    @Query("Delete FROM users_contacts WHERE userLogin = :userLogin")
    fun clear(userLogin: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userWithContactsEntity: UserWithContactsEntity)

}
