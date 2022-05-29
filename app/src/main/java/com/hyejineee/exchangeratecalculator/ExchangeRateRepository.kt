package com.hyejineee.exchangeratecalculator

import com.hyejineee.exchangeratecalculator.data.Country
import com.hyejineee.exchangeratecalculator.data.ExchangeRate
import com.hyejineee.exchangeratecalculator.datasource.local.ExchangeRateLocalDataSource
import com.hyejineee.exchangeratecalculator.datasource.remote.ExchangeRateRemoteDataSource
import com.hyejineee.exchangeratecalculator.exception.NotFoundDataException
import java.net.SocketTimeoutException
import kotlin.math.floor
import javax.inject.Inject

interface ExchangeRateRepository {
    suspend fun getExchangeRate(from: Country, to: Country): ExchangeRate
    suspend fun getAllExchangeRate()
}

class ExchangeRateRepositoryImpl @Inject constructor(
    private val exchangeRateRemoteDatasource: ExchangeRateRemoteDataSource,
    private val exchangeRateLocalDataSource: ExchangeRateLocalDataSource
) : ExchangeRateRepository {

    private val nameCurrencyMap = HashMap<String, String>().apply {
        put("USD", "미국")
        put("KRW", "한국")
        put("JPY", "일본")
        put("PHP", "필리핀")
    }


    override suspend fun getExchangeRate(from: Country, to: Country): ExchangeRate {
        return exchangeRateLocalDataSource.getExchangeRate(from.currency, to.currency)
            ?: throw NotFoundDataException("환율 데이터가 없습니다.")
    }

    override suspend fun getAllExchangeRate() {
        val infos = exchangeRateRemoteDatasource.getAllExchangeRate()

        infos?.quotes?.map {
            val from = it.key.slice(0..2)
            val to = it.key.slice(3..5)

            ExchangeRate(
                from = Country(name = nameCurrencyMap[from]!!, currency = from),
                to = Country(name = nameCurrencyMap[to]!!, currency = to),
                collectionTime = infos.timestamp * 1000,
                exchangeRate = floor(it.value * 100) / 100.0
            )
        }?.toTypedArray()?.let {
            exchangeRateLocalDataSource.insertAllExchangeRate(*it)
        }

    }


}
