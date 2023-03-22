package com.example.emplyapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApplicationAPI {
    @GET("application/{jobseeker_id}")
    fun getApplications(
        @Path("jobseeker_id") jobseeker_id: Int
    ): Call<List<ApplicationClass>>

    @GET("/application-em/{employer_id}")
    fun getApplicationEmployer(@Path("employer_id") employer_id: Int): Call<List<ApplicationEmployerClass>>

}