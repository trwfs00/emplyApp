package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginUserClass (
    @Expose
    @SerializedName("success") val success: Int,

    @Expose
    @SerializedName("userid") val userid: Int,

    @Expose
    @SerializedName("username") val username: String,

    @Expose
    @SerializedName("Login_role") val Login_role: Int) {}