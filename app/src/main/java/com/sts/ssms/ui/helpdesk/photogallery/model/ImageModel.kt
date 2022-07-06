package com.sts.ssms.ui.helpdesk.photogallery.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageModel(
    val imgUrl: String,
    val name: String,
    val approvalStatus: String
) : Parcelable