package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JobRecent(
    @Expose
    @SerializedName("job_name") val job_name:String,

    @Expose
    @SerializedName("company_name") val company_name:String,

    @Expose
    @SerializedName("nicename") val nicename:String,

    @Expose
    @SerializedName("type") val type:String,

    @Expose
    @SerializedName("logo") val logo:String,

    @Expose
    @SerializedName("created_at") val created_at:String
){}
