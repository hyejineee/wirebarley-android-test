package com.hyejineee.exchangeratecalculator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExchangeRateCalcApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}