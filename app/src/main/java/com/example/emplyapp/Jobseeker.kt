package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.DateFormat

data class Jobseeker(

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
    @SerializedName("Login_id") val Login_id: Int,

    @Expose
    @SerializedName("country_id") val country_id: Int,

    @Expose
    @SerializedName("picture") val picture: String
)
