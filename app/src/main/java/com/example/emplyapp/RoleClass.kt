package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RoleClass(
    @Expose
    @SerializedName("Login_id") val Login_id: Int,

    @Expose
    @SerializedName("Login_role") val Login_role: Int,

    @Expose
    @SerializedName("username") val username: String,

    @Expose
    @SerializedName("password") val password: String,
)

