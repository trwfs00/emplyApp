package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployerClass(
    @Expose
    @SerializedName("fullName") val fullName: String,

    @Expose
    @SerializedName("nickName") val nickName: String,

    @Expose
    @SerializedName("birthday") val birthday: String,

    @Expose
    @SerializedName("phone") val phone: String,

    @Expose
    @SerializedName("gender") val gender: Int,

    @Expose
    @SerializedName("email") val email: String,

    @Expose
    @SerializedName("status") val status: Int,

    @Expose
    @SerializedName("dept") val dept: String,

    @Expose
    @SerializedName("Login_id") val Login_id: Int,

    @Expose
    @SerializedName("company_id") val company_id: Int,

    @Expose
    @SerializedName("country_id") val country_id: Int
)
