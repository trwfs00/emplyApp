package com.example.emplyapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryAPI {

    @GET("/all-category")
    fun getAllCategory(): Call<List<CategoryClass>>

    @GET("/category/{key}")
    fun getCategory(
        @Path("key")key: String
    ): Call<List<CategoryClass>>

    companion object {
        fun create():CategoryAPI {
            val categoryClient : CategoryAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CategoryAPI::class.java)
            return categoryClient
        }
    }
}