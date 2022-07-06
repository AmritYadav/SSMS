package com.sts.ssms.ui.search.model

import android.os.Parcelable

interface SearchItem : Parcelable {
    val id: Int
}

interface VendorStaffSearchItem : SearchItem {
    val name: String
    val phoneNumber: String
    val categoryName: String
    val status: String
}