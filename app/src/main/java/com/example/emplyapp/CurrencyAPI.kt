package com.example.emplyapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CurrencyAPI {
    @GET("all-currencies")
    fun getAllCurrency(): Call<List<CurrencyClass>>

    companion object {
        fun create():CurrencyAPI {
            val currencyClient : CurrencyAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CurrencyAPI::class.java)
            return currencyClient
        }
    }
}