package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JobsClass(
    @Expose
    @SerializedName("job_name") val job_name: String,

    @Expose
    @SerializedName("salaryFrom") val salaryFrom: Int,

    @Expose
    @SerializedName("salaryTo") val salaryTo: Int,

    @Expose
    @SerializedName("description") val description: String,

    @Expose
    @SerializedName("minimumQualification") val minimumQualification: String,

    @Expose
    @SerializedName("type") val type: String,

    @Expose
    @SerializedName("category_id") val category_id: Int,

    @Expose
    @SerializedName("employer_id") val employer_id: Int,

    @Expose
    @SerializedName("benefit") val benefit: String,

    @Expose
    @SerializedName("code") val code: String
)
