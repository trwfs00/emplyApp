package com.example.emplyapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryClass (
    @Expose
    @SerializedName("category_name") val category_name: String) {}