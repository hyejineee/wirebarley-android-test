package com.hyejineee.exchangeratecalculator.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hyejineee.exchangeratecalculator.data.ExchangeRate

@Dao
interface ExchangeRateDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExchangeRate(data: ExchangeRate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllExchangeRate(vararg data: ExchangeRate)

    @Query("select * from ExchangeRate where from_currency =:from and to_currency =:to")
    fun getExchangeRate(from: String, to: String):ExchangeRate?
}