package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApplyClass(
    @Expose
    @SerializedName("job_id") val job_id : Int,

    @Expose
    @SerializedName("resume") val resume : String,

    @Expose
    @SerializedName("motivation") val motivation : String,

    @Expose
    @SerializedName("jobseeker_id") val jobseeker_id : Int
)
