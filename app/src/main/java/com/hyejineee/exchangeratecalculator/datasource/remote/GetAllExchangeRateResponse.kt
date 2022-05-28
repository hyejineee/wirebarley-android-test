package com.hyejineee.exchangeratecalculator.datasource.remote

data class GetAllExchangeRateResponse(
    val quotes: HashMap<String, Double>,
    val source: String,
    val success: Boolean,
    val timestamp: Long
)