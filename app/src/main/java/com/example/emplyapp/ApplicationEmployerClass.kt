package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApplicationEmployerClass(
    @SerializedName("job_id") val job_id: Int,
    @SerializedName("job_name") val job_name: String,
    @SerializedName("company_name") val company_name: String,
    @SerializedName("logo") val logo: String,
    @SerializedName("status") val status: String,
    @SerializedName("applicationCount") val applicationCount: Int
) {
}
