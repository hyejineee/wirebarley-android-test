package com.hyejineee.exchangeratecalculator.di

import android.content.Context
import androidx.room.Room
import com.hyejineee.exchangeratecalculator.datasource.local.AppDatabase
import com.hyejineee.exchangeratecalculator.datasource.local.ExchangeRateDAO
import com.hyejineee.exchangeratecalculator.datasource.local.ExchangeRateLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DBModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "app.db")
        .build()

    @Provides
    fun provideExchangeRateDAO(database: AppDatabase) = database.exchangedRateDAO()

}