package com.hyejineee.exchangeratecalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.hyejineee.exchangeratecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel(ExchangeRateRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initSubscribe()
    }

    private fun initView() {
        ArrayAdapter.createFromResource(
            this,
            R.array.recipientCountry,
            android.R.layout.simple_spinner_item
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.recipientCountrySpinner.adapter = adapter
            }

        binding.recipientCountrySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selected =
                        resources.getStringArray(R.array.recipientCountry)[position].let {
                            val country = it.replace("(", "")
                                .replace(")", "")
                                .split(" ")
                            Country(name = country[0], currency = country[1])
                        }

                    binding.remittanceEditTextView.setText("")
                    viewModel.selectRecipientCountry(selected)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        binding.calculateButton.setOnClickListener {
            val money = binding.remittanceEditTextView.text.toString().toInt()
            viewModel.calculate(money)
        }

    }

    private fun initSubscribe() {
        viewModel.exchangeRate.observe(this) {
            binding.collectionTime = it.collectionTime
            binding.exchangeRate = it.exchangeRate
            binding.selectedCountry = it.to
        }

        viewModel.received.observe(this) {
            binding.received = it
        }

    }


}