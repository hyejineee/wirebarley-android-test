package com.hyejineee.exchangeratecalculator.data

import androidx.room.Embedded
import androidx.room.Entity
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

@Entity(primaryKeys = ["from_currency", "to_currency"])
data class ExchangeRate(
    @Embedded(prefix = "from_") val from: Country,
    @Embedded(prefix = "to_") val to: Country,
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
