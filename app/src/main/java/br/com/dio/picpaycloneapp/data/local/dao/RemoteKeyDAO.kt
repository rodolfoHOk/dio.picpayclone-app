package br.com.dio.picpaycloneapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dio.picpaycloneapp.data.local.entities.RemoteKey

@Dao
interface RemoteKeyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE login = :login AND transaction_code = :transactionCode")
    suspend fun getRemoteKeyByLoginAndTransactionCode(
        login: String, transactionCode: String
    ): RemoteKey?

    @Query("DELETE FROM remote_keys WHERE login = :login")
    suspend fun clearByUserLogin(login: String)

}
