package com.hyejineee.exchangeratecalculator.datasource.remote

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
    ) = withContext(Dispatchers.IO) {
        // TODO: timeout 예외처리 필요 -> 코루틴 예외처리 하는 방법 공부 
        val response = exchangeRateService.getAllExchangeRate(
            source,
            currencies
        ).execute()

        response.body()
    }

}
