package com.hyejineee.exchangeratecalculator.datasource

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Inject

interface ExchangeRateAPI {
    @Headers("apikey: knNE0Xc0nbuPOiKn08IkDIk6G6cD54sx")
    @GET("live")
    fun getAllExchangeRate(
        @Query("source") source: String,
        @Query("currencies") currencies: String
    ): Call<GetAllExchangeRateResponse>
}

class ExchangeRateService @Inject constructor(
    private val exchangeRateApi: ExchangeRateAPI
) {
    fun getAllExchangeRate(source:String, currencies: List<String>): Call<GetAllExchangeRateResponse> {
        return exchangeRateApi.getAllExchangeRate(
            source = "USD",
            currencies = currencies.joinToString(",")
        )
    }
}
