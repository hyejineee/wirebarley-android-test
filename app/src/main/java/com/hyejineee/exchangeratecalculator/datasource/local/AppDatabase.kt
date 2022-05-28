package com.hyejineee.exchangeratecalculator.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hyejineee.exchangeratecalculator.data.ExchangeRate

@Database(entities = [ExchangeRate::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun exchangedRateDAO() : ExchangeRateDAO
}