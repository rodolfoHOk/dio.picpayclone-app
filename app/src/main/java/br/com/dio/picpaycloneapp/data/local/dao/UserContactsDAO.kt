package br.com.dio.picpaycloneapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import br.com.dio.picpaycloneapp.data.local.entities.UserContactCrossRef
import br.com.dio.picpaycloneapp.data.local.entities.UserEntity

@Dao
interface UserContactsDAO {

    @Transaction
    @Query("SELECT u.* FROM users_contacts uc INNER JOIN users u ON uc.user_login = u.login WHERE user_login = :userLogin")
    fun getUserContactsByUserLogin(userLogin: String) : List<UserEntity>

    @Query("Delete FROM users_contacts WHERE user_login = :userLogin")
    fun clearByUserLogin(userLogin: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userContactCrossRef: UserContactCrossRef)

}
