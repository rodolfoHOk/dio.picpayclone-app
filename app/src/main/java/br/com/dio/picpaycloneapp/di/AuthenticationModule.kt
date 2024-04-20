package br.com.dio.picpaycloneapp.di

import br.com.dio.picpaycloneapp.repositories.AuthenticationRepository
import br.com.dio.picpaycloneapp.repositories.impl.AuthenticationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthenticationModule {

    @Binds
    abstract fun providesAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

}
