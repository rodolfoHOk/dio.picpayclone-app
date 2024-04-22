package br.com.dio.picpaycloneapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import br.com.dio.picpaycloneapp.data.local.entities.UserContactCrossRef
import br.com.dio.picpaycloneapp.data.local.entities.UserWithContactsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserContactsDAO {

    @Transaction
    @Query("SELECT * FROM users WHERE login = :userLogin")
    fun getUserContactsByUserLogin(userLogin: String) : Flow<UserWithContactsEntity>

    @Query("Delete FROM users_contacts WHERE user_login = :userLogin")
    fun clearByUserLogin(userLogin: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userContactCrossRef: UserContactCrossRef)

}
