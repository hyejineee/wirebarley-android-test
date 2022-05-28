package com.hyejineee.exchangeratecalculator.data

import com.hyejineee.exchangeratecalculator.data.Country
import com.hyejineee.exchangeratecalculator.data.ExchangeRate
import java.text.DecimalFormat
import java.text.SimpleDateFormat

data class ExchangeRateUiModel(
    val from: Country,
    val to: Country,
    val collectionTime: String,
    val exchangeRate: String
) {
    fun toDomainModel() = ExchangeRate(
        from = from,
        to = to,
        collectionTime = SimpleDateFormat("yyyy-MM-dd HH:mm").parse(collectionTime).time,
        exchangeRate = DecimalFormat("#,###.00").parse(exchangeRate).toDouble()
    )
}
