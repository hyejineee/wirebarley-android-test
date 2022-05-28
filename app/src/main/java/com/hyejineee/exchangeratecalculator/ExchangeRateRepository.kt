package com.hyejineee.exchangeratecalculator.data

import javax.inject.Inject

interface ExchangeRateRepository {
    suspend fun getExchangeRate(from: Country, to: Country): ExchangeRate
    suspend fun getAllExchangeRate()
}

class ExchangeRateRepositoryImpl @Inject constructor(
     private val exchangeRateRemoteDatasource:ExchangeRateRemoteDataSource
) : ExchangeRateRepository {


    override suspend fun getExchangeRate(from: Country, to: Country): ExchangeRate {

        val data = exchangeRateRemoteDatasource.getAllExchangeRate()



        return ExchangeRate(
            from = Country("미국", "USD"),
            to = to,
            collectionTime = System.currentTimeMillis(),
            exchangeRate = 1121.419945
        )
    }

    override suspend fun getAllExchangeRate() {
        val data = exchangeRateRemoteDatasource.getAllExchangeRate()

        

    }

}
