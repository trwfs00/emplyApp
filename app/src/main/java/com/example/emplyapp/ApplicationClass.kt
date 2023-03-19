package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApplicationClass(
    @Expose
    @SerializedName("job_name") val job_name: String,

    @Expose
    @SerializedName("company_name") val company_name: String,

    @Expose
    @SerializedName("logo") val logo: String) {}

