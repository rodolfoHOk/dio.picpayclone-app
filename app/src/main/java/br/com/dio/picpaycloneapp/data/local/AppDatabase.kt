package br.com.dio.picpaycloneapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.dio.picpaycloneapp.data.local.dao.RemoteKeyDAO
import br.com.dio.picpaycloneapp.data.local.dao.TransactionDAO
import br.com.dio.picpaycloneapp.data.local.dao.UserContactsDAO
import br.com.dio.picpaycloneapp.data.local.dao.UserDAO
import br.com.dio.picpaycloneapp.data.local.entities.RemoteKey
import br.com.dio.picpaycloneapp.data.local.entities.TransactionEntity
import br.com.dio.picpaycloneapp.data.local.entities.UserContactCrossRef
import br.com.dio.picpaycloneapp.data.local.entities.UserEntity

@Database(
    entities = [
        TransactionEntity::class,
        UserEntity::class,
        UserContactCrossRef::class,
        RemoteKey::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transitionDAO(): TransactionDAO
    abstract fun userDAO(): UserDAO
    abstract fun userContactsDAO(): UserContactsDAO
    abstract fun remoteKeyDAO(): RemoteKeyDAO

    companion object {

        private const val DATABASE_NAME = "picpayclone.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}
