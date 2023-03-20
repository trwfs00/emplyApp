package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyClass(
    @Expose
    @SerializedName("code") val code  : String? = null,

    @Expose
    @SerializedName("description") val description : String? = null
)
