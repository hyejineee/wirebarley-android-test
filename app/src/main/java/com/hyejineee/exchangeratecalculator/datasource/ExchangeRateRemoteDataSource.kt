package com.hyejineee.exchangeratecalculator.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ExchangeRateRemoteDataSource @Inject constructor(
    private val exchangeRateService: ExchangeRateService
) {

    suspend fun getAllExchangeRate(
        source: String = "USD",
        currencies: List<String> = listOf("KRW", "JPY", "PHP")
    ) = coroutineScope {
        val response = withContext(Dispatchers.IO) {
            exchangeRateService.getAllExchangeRate(
                source,
                currencies
            ).execute()
        }

        response.body()
    }
}
