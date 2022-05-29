package com.hyejineee.exchangeratecalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.hyejineee.exchangeratecalculator.data.Country
import com.hyejineee.exchangeratecalculator.databinding.ActivityMainBinding
import com.hyejineee.exchangeratecalculator.exception.NotFoundDataException
import dagger.hilt.android.AndroidEntryPoint
import java.net.SocketTimeoutException
import java.text.DecimalFormat

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val country = listOf(
        Country("한국", "KRW"),
        Country("일본", "JPY"),
        Country("필리핀", "PHP"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchData()

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
                    val selected = country[position]
                    binding.remittanceEditTextView.setText("")
                    viewModel.selectRecipientCountry(selected)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        binding.calculateButton.setOnClickListener {
            val money = binding.remittanceEditTextView.text.toString().toInt()
            viewModel.calculate(money)
        }

        binding.loadingLayout.setOnTouchListener { v, event -> true }

        (binding.noticeLayout.findViewById(R.id.retryButton) as Button).setOnClickListener {
            binding.noticeLayout.isVisible = false
            viewModel.fetchData(country[binding.recipientCountrySpinner.selectedItemPosition])
        }

        binding.remittanceEditTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.also {
                    if (it.isNotEmpty()) {
                        val num = it.toString().toInt()
                        binding.remittanceInputLayout.error =
                            if (num < 0 || num > 10000) "송금액이 바르지 않습니다." else null

                    }
                }
            }

        })

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

        viewModel.isLoading.observe(this) {
            binding.loadingLayout.isVisible = it
        }

        viewModel.error.observe(this) {


            when (it) {
                is NotFoundDataException -> {
                    binding.noticeLayout.isVisible = true
                    (binding.noticeLayout.findViewById(R.id.noticeTextview) as TextView).text =
                        resources.getString(R.string.notFoundDataNotice)
                    binding.noticeLayout.setOnTouchListener { v, event -> true }
                }
                is SocketTimeoutException -> {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.networkErrorNotice),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    binding.noticeLayout.isVisible = true
                    (binding.noticeLayout.findViewById(R.id.noticeTextview) as TextView).text =
                        resources.getString(R.string.unknownErrorNotice)
                    binding.noticeLayout.setOnTouchListener { v, event -> true }
                }
            }

        }
    }
}