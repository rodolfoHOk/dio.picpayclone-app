package br.com.dio.picpaycloneapp.data.repositories

import androidx.room.withTransaction
import br.com.dio.picpaycloneapp.data.local.AppDatabase
import br.com.dio.picpaycloneapp.data.local.entities.UserContactCrossRef
import br.com.dio.picpaycloneapp.data.local.mappers.toEntity
import br.com.dio.picpaycloneapp.data.local.mappers.toModel
import br.com.dio.picpaycloneapp.domain.models.Balance
import br.com.dio.picpaycloneapp.domain.models.User
import br.com.dio.picpaycloneapp.domain.repositories.UserRepository
import br.com.dio.picpaycloneapp.data.remote.services.ApiService
import br.com.dio.picpaycloneapp.utils.InternetConnection
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : UserRepository {

    override suspend fun getUserBalance(login: String): Balance {
        return apiService.getUserBalance(login)
    }

    override suspend fun getUserByLogin(login: String): User {
        return apiService.getUserByLogin(login)
    }

    override suspend fun getUserContacts(login: String): List<User> {
        val userContacts = if (InternetConnection.Status.isAvailable &&
            InternetConnection.Status.hasInternet
        ) {
            val contacts = apiService.getUserContacts(login)
            appDatabase.withTransaction {
                appDatabase.userContactsDAO().clearByUserLogin(login)

                contacts.forEach { user ->
                    val userEntity = user.toEntity()
                    appDatabase.userDAO().insert(userEntity)
                    appDatabase.userContactsDAO().insert(
                        UserContactCrossRef(
                            userLogin = login,
                            contactLogin = userEntity.login
                        )
                    )
                }
            }
            contacts
        } else {
            val usersEntity = appDatabase.userContactsDAO().getUserContactsByUserLogin(login)
            val userContacts = usersEntity.map { userEntity -> userEntity.toModel() }
            userContacts
        }
        return userContacts
    }

}
