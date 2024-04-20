package br.com.dio.picpaycloneapp.di

import br.com.dio.picpaycloneapp.repositories.TransactionRepository
import br.com.dio.picpaycloneapp.repositories.impl.TransactionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TransactionModule {

    @Binds
    abstract fun providesTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository

}
