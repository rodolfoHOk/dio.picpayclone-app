package br.com.dio.picpaycloneapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.dio.picpaycloneapp.data.dao.TransactionDAO
import br.com.dio.picpaycloneapp.data.dao.UserContactsDAO
import br.com.dio.picpaycloneapp.data.dao.UserDAO
import br.com.dio.picpaycloneapp.data.entities.TransactionEntity
import br.com.dio.picpaycloneapp.data.entities.UserContactCrossRef
import br.com.dio.picpaycloneapp.data.entities.UserEntity

@Database(
    entities = [
        TransactionEntity::class,
        UserEntity::class,
        UserContactCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transitionDAO(): TransactionDAO

    abstract fun userDAO(): UserDAO

    abstract fun userContactsDAO(): UserContactsDAO

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
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}
