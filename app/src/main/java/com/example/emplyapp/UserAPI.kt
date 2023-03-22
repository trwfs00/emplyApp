package com.example.emplyapp

import android.provider.ContactsContract.Profile
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UserAPI {
    @FormUrlEncoded
    @POST("jobs")
    fun insertJobs(
        @Field("job_name") job_name: String,
        @Field("salaryFrom") salaryFrom: Int,
        @Field("salaryTo") salaryTo: Int,
        @Field("description") description: String,
        @Field("minimumQualification") minimumQualification: String,
        @Field("type") type: String,
        @Field("category_id") category_id: Int,
        @Field("employer_id") employer_id: Int,
        @Field("benefit") benefit: String,
        @Field("code") code: String
    ):Call<JobsClass>

    @FormUrlEncoded
    @POST("apply")
    fun insertApply(
        @Field("job_id") job_id: Int,
        @Field("resume") resume: String,
        @Field("motivation") motivation: String,
        @Field("jobseeker_id") jobseeker_id: Int
    ):Call<ApplyClass>

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

    @GET("login/{username}")
    fun getLoginId(
        @Path("username") username: String
    ):Call<RoleClass>

    @GET("fetch/{username}")
    fun fetchUserData(@Path("username") username: String): Call<UserDataClass>

    @GET("fetch/{username}")
    fun fetchJobSeekData(@Path("username") username: String): Call<JobseekerClass>

    @PUT("jobseeker/delete/{Login_id}")
    fun softDeleteJobseeker(
        @Path("Login_id") Login_id: Int): Call<UserDataClass>

    @PUT("employer/delete/{Login_id}")
    fun softDeleteEmployer(
        @Path("Login_id") Login_id: Int): Call<UserDataClass>

    @FormUrlEncoded
    @POST("jobseeker")
    fun insertProfile(
        @Field("fullName") fullName: String,
        @Field("nickName") nickName: String,
        @Field("birthday") birthday: String,
        @Field("phone") phone: String,
        @Field("gender") gender: Int,
        @Field("email") email: String,
        @Field("Login_id") Login_id: Int,
        @Field("country_id") country_id: Int,
        @Field("picture_jobseek") picture_jobseek: String
    ):Call<Jobseeker>

    @FormUrlEncoded
    @PUT("jobseeker/{Login_id}")
    fun editJobseeker(
        @Field("fullName") fullName: String,
        @Field("nickName") nickName: String,
        @Field("birthday") birthday: String,
        @Field("picture_jobseek") picture_jobseek: String,
        @Path("Login_id") Login_id: Int
    ):Call<ProfileClass>

    @FormUrlEncoded
    @PUT("employer/{Login_id}")
    fun editEmployer(
        @Field("fullName") fullName: String,
        @Field("nickName") nickName: String,
        @Field("birthday") birthday: String,
        @Field("picture_emp") picture_emp: String,
        @Path("Login_id") Login_id: Int
    ):Call<ProfileClass>

    @FormUrlEncoded
    @POST("employer")
    fun insertEmployer(
        @Field("fullName") fullName: String,
        @Field("nickName") nickName: String,
        @Field("birthday") birthday: String,
        @Field("phone") phone: String,
        @Field("gender") gender: Int,
        @Field("email") email: String,
        @Field("empcard") empcard: String,
        @Field("status") status: Int,
//        @Field("picture_emp") picture_emp: String,
        @Field("dept") dept: String,
        @Field("Login_id") Login_id: Int,
        @Field("company_id") company_id: Int,
        @Field("country_id") country_id: Int
    ):Call<EmployerClass>

    @FormUrlEncoded
    @POST("company")
    fun insertCompany(
        @Field("company_name") company_name: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("address") address: String,
        @Field("logo") logo: String
    ):Call<CompanyClass>

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