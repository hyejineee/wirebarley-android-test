package com.hyejineee.exchangeratecalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyejineee.exchangeratecalculator.data.Country
import com.hyejineee.exchangeratecalculator.data.ExchangeRateUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DecimalFormat
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) : ViewModel() {

    private val _exchangeRate = MutableLiveData<ExchangeRateUiModel>()
    val exchangeRate: LiveData<ExchangeRateUiModel> = _exchangeRate

    private val _received = MutableLiveData<String>()
    val received: LiveData<String> = _received

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _isLoading.value = true
        viewModelScope.launch {
            exchangeRateRepository.getAllExchangeRate()
            _isLoading.value = false
        }

    }


    fun selectRecipientCountry(country: Country) {
        _isLoading.value = true
        viewModelScope.launch {
            val new = exchangeRateRepository.getExchangeRate(
                from = Country("미국", "USD"),
                to = country
            )
            _exchangeRate.value = new.toUiModel()
            _received.value = ""
            _isLoading.value = false
        }
    }

    fun calculate(money: Int) {
        _exchangeRate.value?.toDomainModel()?.also {
            _received.value = DecimalFormat("#,###.00").format(it.exchangeRate * money)
        }
    }

}