package com.hyejineee.exchangeratecalculator.di

import com.hyejineee.exchangeratecalculator.datasource.remote.ExchangeRateAPI
import com.hyejineee.exchangeratecalculator.datasource.remote.ExchangeRateRemoteDataSource
import com.hyejineee.exchangeratecalculator.datasource.remote.ExchangeRateService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideExchangeRateAPI(): ExchangeRateAPI {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.apilayer.com/currency_data/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRateAPI::class.java)
    }

    @Provides
    fun provideExchangeRateService(api: ExchangeRateAPI) = ExchangeRateService(api)

}