package com.example.emplyapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UserAPI {

    @FormUrlEncoded
    @POST("register")
    fun insertUser(
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("role") role: Int
    ):Call<RoleClass>

    @GET("login/{username}/{password}")
    fun userLogin(
        @Path("username") username: String,
        @Path("password") password: String
    ): Call<LoginUserClass>

    companion object
    {
        fun create():UserAPI {
            val userClient : UserAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserAPI::class.java)
            return userClient

            /*val Client: UserAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserAPI::class.java)
            return Client*/
        }
    }
}