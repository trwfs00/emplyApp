package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CompanyClass(
    @Expose
    @SerializedName("company_id") val company_id: Int,

    @Expose
    @SerializedName("company_name") val company_name: String,

    @Expose
    @SerializedName("country_id") val country_id: Int,

    @Expose
    @SerializedName("state") val state: String,

    @Expose
    @SerializedName("address") val address: String,

    @Expose
    @SerializedName("logo") val logo: String
)
