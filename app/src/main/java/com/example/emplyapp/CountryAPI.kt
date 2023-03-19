package com.example.emplyapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryAPI {
    @GET("/all-country")
    fun getAllCountry(): Call<List<CountryClass>>

    @GET("/country/{key}")
    fun getCountry(
        @Path("key")key: String
    ): Call<List<CountryClass>>

    companion object {
        fun create():CountryAPI {
            val countryClient : CountryAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountryAPI::class.java)
            return countryClient
        }
    }

}