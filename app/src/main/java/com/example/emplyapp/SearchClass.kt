package com.example.emplyapp

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchClass(
    @Expose
    @SerializedName("job_name") val job_name : String? = null,

    @Expose
    @SerializedName("company_name") val company_name : String? = null,

    @Expose
    @SerializedName("country_name") val country_name : String? = null,

    @Expose
    @SerializedName("state") val state : String? = null,

    @Expose
    @SerializedName("salaryFrom") val salaryFrom : Int,

    @Expose
    @SerializedName("salaryTo") val salaryTo : Int,

    @Expose
    @SerializedName("type") val type : String? = null,

    @Expose
    @SerializedName("description") val description : String? = null,

    @Expose
    @SerializedName("minimumQualification") val minimumQualification : String? = null,

    @Expose
    @SerializedName("benefit") val benefit : String? = null,

    @Expose
    @SerializedName("category_name") val category_name : String? = null,

    @Expose
    @SerializedName("logo") val logo : String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(job_name)
        parcel.writeString(company_name)
        parcel.writeString(country_name)
        parcel.writeString(state)
        parcel.writeInt(salaryFrom)
        parcel.writeInt(salaryTo)
        parcel.writeString(type)
        parcel.writeString(description)
        parcel.writeString(minimumQualification)
        parcel.writeString(benefit)
        parcel.writeString(category_name)
        parcel.writeString(logo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchClass> {
        override fun createFromParcel(parcel: Parcel): SearchClass {
            return SearchClass(parcel)
        }

        override fun newArray(size: Int): Array<SearchClass?> {
            return arrayOfNulls(size)
        }
    }
}
