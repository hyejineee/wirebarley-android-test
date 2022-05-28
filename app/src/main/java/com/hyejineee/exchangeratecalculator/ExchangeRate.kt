package com.hyejineee.exchangeratecalculator

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

data class ExchangeRate(
    val from: Country,
    val to: Country,
    val collectionTime: Long,
    val exchangeRate: Double
) {
    fun toUiModel() = ExchangeRateUiModel(
        from = from,
        to = to,
        collectionTime = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(collectionTime)),
        exchangeRate = DecimalFormat("#,###.00").format(exchangeRate)
    )
}
