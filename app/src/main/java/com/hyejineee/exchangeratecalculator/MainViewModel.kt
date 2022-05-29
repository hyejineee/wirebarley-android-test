package com.hyejineee.exchangeratecalculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyejineee.exchangeratecalculator.data.Country
import com.hyejineee.exchangeratecalculator.data.ExchangeRateUiModel
import com.hyejineee.exchangeratecalculator.exception.NotFoundDataException
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import java.text.DecimalFormat
import kotlinx.coroutines.CoroutineExceptionHandler
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

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error


    private val ceh = CoroutineExceptionHandler { _, exception ->
        Log.e("mainViewModel", exception.toString())
        _error.value = exception
    }

    fun fetchData(country: Country? = null) {
        _isLoading.value = true
        viewModelScope.launch(ceh) {
            exchangeRateRepository.getAllExchangeRate()
            country?.also {
                selectRecipientCountry(country)
            }
            _isLoading.value = false
        }
    }


    fun selectRecipientCountry(country: Country) {
        _isLoading.value = true
        viewModelScope.launch(ceh) {
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