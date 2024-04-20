package br.com.dio.picpaycloneapp.di

import br.com.dio.picpaycloneapp.domain.repositories.UserRepository
import br.com.dio.picpaycloneapp.data.repositories.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserModule {

    @Binds
    abstract fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository

}
