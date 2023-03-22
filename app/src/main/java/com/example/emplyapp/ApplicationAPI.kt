package com.example.emplyapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApplicationAPI {
    @GET("application/{jobseeker_id}")
    fun getApplications(
        @Path("jobseeker_id") jobseeker_id: Int
    ): Call<List<ApplicationClass>>

    @GET("application-em/{employer_id}")
    fun getApplicationEmployer(@Path("employer_id") employer_id: Int): Call<List<ApplicationEmployerClass>>

    @GET("application/apply/{job_id}")
    fun getApplicationsApply(
        @Path("job_id") job_id: Int
    ): Call<ActivityChooseClass>

    companion object
    {
        fun create():ApplicationAPI {
            val ApplicationClient : ApplicationAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApplicationAPI::class.java)
            return ApplicationClient

            /*val Client: UserAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserAPI::class.java)
            return Client*/
        }
    }

}