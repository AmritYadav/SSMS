package com.sts.ssms.utils

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class NavType : Parcelable {
    VENDOR,
    STAFF,
    TICKET,
    PHOTO_GALLERY,
    NOTICE_BOARD,
    DOCUMENTS,
    ALL_AMENITY_REQUEST,
    OFFICIAL_COMMUNICATION
}