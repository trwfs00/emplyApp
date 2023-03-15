import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchHomeClass(
    @Expose
    @SerializedName("job_id") val job_id: Int,

    @Expose
    @SerializedName("job_name") val job_name: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(job_id)
        parcel.writeString(job_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchHomeClass> {
        override fun createFromParcel(parcel: Parcel): SearchHomeClass {
            return SearchHomeClass(parcel)
        }

        override fun newArray(size: Int): Array<SearchHomeClass?> {
            return arrayOfNulls(size)
        }
    }
}