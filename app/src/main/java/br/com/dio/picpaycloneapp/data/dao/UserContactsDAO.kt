package br.com.dio.picpaycloneapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dio.picpaycloneapp.data.entities.UserWithContactsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserContactsDAO {

    @Query("SELECT * FROM UserContactCrossRef WHERE userLogin = :userLogin")
    fun getUserContactsByUserLogin(userLogin: String) : Flow<UserWithContactsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(userWithContactsEntity: UserWithContactsEntity)

}
