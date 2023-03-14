package com.example.emplyapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryAPI {
    @GET("category-names")
    fun getCategory(): Call<List<CategoryClass>>

}