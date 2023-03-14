package com.example.emplyapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApplicationAPI {
    @GET("/application/{jobseeker_id}")
    fun getApplications(
        @Path("job_name") job_name: String,
        @Path("company_name") company_name: String,
        @Path("logo") logo: String
    ): Call<List<ApplicationClass>>
}