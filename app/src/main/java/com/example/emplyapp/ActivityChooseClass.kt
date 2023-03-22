package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ActivityChooseClass(
    @Expose
    @SerializedName("job_name") val job_name: String,

    @Expose
    @SerializedName("fullName") val fullName: String,

    @Expose
    @SerializedName("created_at") val created_at: String,

    @Expose
    @SerializedName("picture_jobseek") val picture_jobseek: String
)
