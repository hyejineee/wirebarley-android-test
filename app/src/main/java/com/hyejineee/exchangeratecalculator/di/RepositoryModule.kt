package com.hyejineee.exchangeratecalculator.di

import com.hyejineee.exchangeratecalculator.ExchangeRateRepository
import com.hyejineee.exchangeratecalculator.ExchangeRateRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindsExchangeRateRepository(
        exchangeRateRepo: ExchangeRateRepositoryImpl
    ): ExchangeRateRepository
}