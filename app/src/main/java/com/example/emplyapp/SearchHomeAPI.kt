package com.example.emplyapp

import SearchHomeClass
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchHomeAPI {
    @GET("/all-jobs")
    fun getAllJobs(): Call<List<SearchHomeClass>>

    @GET("/job/{key}")
    fun getJob(
        @Path("key")key: String
    ): Call<List<SearchHomeClass>>

    companion object {
        fun create():SearchHomeAPI {
            val SearchHomeClient : SearchHomeAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3003/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SearchHomeAPI::class.java)
            return SearchHomeClient
        }
    }
}