package com.example.emplyapp

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryClass(
    @Expose
    @SerializedName("category_id") val category_id  : Int,

    @Expose
    @SerializedName("category_name") val category_name : String? = null,

    @Expose
    @SerializedName("detail") val detail : String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(category_id)
        parcel.writeString(category_name)
        parcel.writeString(detail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryClass> {
        override fun createFromParcel(parcel: Parcel): CategoryClass {
            return CategoryClass(parcel)
        }

        override fun newArray(size: Int): Array<CategoryClass?> {
            return arrayOfNulls(size)
        }
    }
}