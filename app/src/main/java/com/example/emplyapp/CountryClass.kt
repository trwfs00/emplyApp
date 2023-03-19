package com.example.emplyapp

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountryClass(
    @Expose
    @SerializedName("country_id") val country_id: Int,

    @Expose
    @SerializedName("nicename") val nicename: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(country_id)
        parcel.writeString(nicename)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryClass> {
        override fun createFromParcel(parcel: Parcel): CountryClass {
            return CountryClass(parcel)
        }

        override fun newArray(size: Int): Array<CountryClass?> {
            return arrayOfNulls(size)
        }
    }
}
