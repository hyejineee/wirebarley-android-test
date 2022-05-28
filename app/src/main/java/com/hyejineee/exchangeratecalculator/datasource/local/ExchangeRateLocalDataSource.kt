package com.hyejineee.exchangeratecalculator.datasource.local

import com.hyejineee.exchangeratecalculator.data.ExchangeRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExchangeRateLocalDataSource @Inject constructor(
    private val exchangeRateDAO: ExchangeRateDAO
) {
    suspend fun getExchangeRate(from: String, to: String) = withContext(Dispatchers.IO) {
        exchangeRateDAO.getExchangeRate(from, to)
    }

    suspend fun insertAllExchangeRate(vararg data: ExchangeRate) = withContext(Dispatchers.IO) {
        exchangeRateDAO.insertAllExchangeRate(*data)
    }

}
