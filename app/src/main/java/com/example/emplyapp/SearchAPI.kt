package com.example.emplyapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface SearchAPI {
    @GET("all-jobs")
    fun getAllSearch(): Call<List<SearchClass>>

    @GET("all-jobs/{key}")
    fun getSearch(
        @Path("key")key: String
    ): Call<List<SearchClass>>

    @FormUrlEncoded
    @POST("apply")
    fun insertApply(
        @Field("job_id") job_id: Int,
        @Field("resume") resume: String,
        @Field("motivation") motivation: String,
        @Field("jobseeker_id") jobseeker_id: Int
    ):Call<ApplyClass>

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