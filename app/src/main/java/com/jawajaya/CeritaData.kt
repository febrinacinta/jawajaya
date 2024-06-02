package com.jawajaya.Database

import android.os.Parcel
import android.os.Parcelable

data class CeritaData(
    val judul: String?,
    val text_cerita: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(judul)
        parcel.writeString(text_cerita)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CeritaData> {
        override fun createFromParcel(parcel: Parcel): CeritaData {
            return CeritaData(parcel)
        }

        override fun newArray(size: Int): Array<CeritaData?> {
            return arrayOfNulls(size)
        }
    }
}
