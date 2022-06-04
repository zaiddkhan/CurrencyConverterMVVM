package com.example.currencyconvertermvvm

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyconvertermvvm.databinding.ActivityMainBinding
import com.example.currencyconvertermvvm.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvert.setOnClickListener {
            viewModel.convert(
                binding.etFrom.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString()
            )
        }
        lifecycleScope.launch{
            viewModel.conversion.collect{
                event ->
                when(event){
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvResult.setTextColor(Color.BLACK)
                        binding.tvResult.text = event.resultText

                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.text = event.errorText


                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}