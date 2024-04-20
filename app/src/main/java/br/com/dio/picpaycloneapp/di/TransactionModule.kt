package br.com.dio.picpaycloneapp.di

import br.com.dio.picpaycloneapp.domain.repositories.TransactionRepository
import br.com.dio.picpaycloneapp.data.repositories.TransactionRepositoryImpl
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
