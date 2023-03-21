package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDataClass(
    @Expose
    @SerializedName("Login_id") val Login_id: Int,

    @Expose
    @SerializedName("Login_role") val Login_role: Int,

    @Expose
    @SerializedName("username") val username: String,

    @Expose
    @SerializedName("password") val password: String,

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
    @SerializedName("country_id") val country_id: Int,

    @Expose
    @SerializedName("picture_jobseek") val picture_jobseek: String,

    @Expose
    @SerializedName("picture_emp") val picture_emp: String,

    @Expose
    @SerializedName("employer_id") val employer_id: Int,

    @Expose
    @SerializedName("empcard") val empcard: String,

    @Expose
    @SerializedName("status") val status: Int,

    @Expose
    @SerializedName("dept") val dept: String,

    @Expose
    @SerializedName("company_id") val company_id: Int,
)
