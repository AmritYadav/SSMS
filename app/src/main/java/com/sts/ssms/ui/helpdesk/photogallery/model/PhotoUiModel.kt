package com.sts.ssms.ui.helpdesk.photogallery.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

fun PhotoUiModel.toImageModel(): ImageModel = ImageModel(
    imgUrl, name, approvalStatus
)

@Parcelize
data class PhotoUiModel(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val createdOn: String,
    val approvalStatus: String
) : Parcelable