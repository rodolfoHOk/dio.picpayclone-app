package br.com.dio.picpaycloneapp.di

import br.com.dio.picpaycloneapp.repositories.UserRepository
import br.com.dio.picpaycloneapp.repositories.impl.UserRepositoryImpl
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
