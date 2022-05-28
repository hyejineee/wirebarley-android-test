package com.hyejineee.exchangeratecalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) : ViewModel() {

    private val _exchangeRate = MutableLiveData<ExchangeRateUiModel>()
    val exchangeRate: LiveData<ExchangeRateUiModel> = _exchangeRate

    private val _received = MutableLiveData<String>()
    val received: LiveData<String> = _received


    fun selectRecipientCountry(country: Country) {
        val new = exchangeRateRepository.getExchangeRate(
            from = Country("미국", "USD"),
            to = country
        )
        _exchangeRate.value = new.toUiModel()
        _received.value = ""
    }

    fun calculate(money: Int) {
        _exchangeRate.value?.toDomainModel()?.also {
            _received.value = DecimalFormat("#,###.00").format(it.exchangeRate * money)
        }
    }

}