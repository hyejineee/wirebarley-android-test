package com.hyejineee.exchangeratecalculator

import javax.inject.Inject

interface ExchangeRateRepository {
    fun getExchangeRate(from: Country, to: Country): ExchangeRate
}

class ExchangeRateRepositoryImpl @Inject constructor() : ExchangeRateRepository {

    override fun getExchangeRate(from: Country, to: Country): ExchangeRate {
        // TODO: 데이터베이스에서 환율 정보 조회한 후에 리턴 캐싱된게 있으면 캐시리턴

        return ExchangeRate(
            from = Country("미국", "USD"),
            to = to,
            collectionTime = System.currentTimeMillis(),
            exchangeRate = 1121.419945
        )
    }

}
