package com.example.currencyconvertermvvm.main

import com.example.currencyconvertermvvm.data.models.CurrencyResponse
import com.example.currencyconvertermvvm.utils.Resource

interface MainRepository {

    suspend fun getRates(base:String):Resource<CurrencyResponse>
}