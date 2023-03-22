package com.example.emplyapp

import AllPostClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AllPostAPI {
    @GET("/all-post/{employer_id}")
    fun getAllPost(@Path("employer_id") employer_id: Int): Call<List<AllPostClass>>
}