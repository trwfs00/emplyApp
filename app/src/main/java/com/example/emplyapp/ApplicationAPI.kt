package com.example.emplyapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApplicationAPI {
    @GET("application/{jobseeker_id}")
    fun getApplications(
        @Path("jobseeker_id") jobseeker_id: Int
    ): Call<List<ApplicationClass>>

}