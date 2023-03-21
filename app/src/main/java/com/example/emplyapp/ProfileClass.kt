package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProfileClass(
    @Expose
    @SerializedName("fullName") val fullName: String,

    @Expose
    @SerializedName("nickName") val nickName: String,

    @Expose
    @SerializedName("birthday") val birthday: String,

    @Expose
    @SerializedName("picture_jobseek") val picture_jobseek: String,

    @Expose
    @SerializedName("picture_emp") val picture_emp: String,

    @Expose
    @SerializedName("Login_id") val Login_id: Int
)
