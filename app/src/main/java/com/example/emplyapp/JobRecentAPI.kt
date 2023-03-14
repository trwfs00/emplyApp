package com.example.emplyapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface JobRecentAPI {
    @GET("recentjobs")
    fun getJobsRecent(): Call<List<JobRecent>>
}

