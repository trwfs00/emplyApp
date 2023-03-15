package com.example.emplyapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchAPI {
    @GET("/all-search")
    fun getAllSearch(): Call<List<SearchClass>>

    @GET("/search/{key}")
    fun getSearch(
        @Path("key")key: String
    ): Call<List<SearchClass>>

    companion object {
        fun create():SearchAPI {
            val searchClient : SearchAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SearchAPI::class.java)
            return searchClient
        }
    }
}