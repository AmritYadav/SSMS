package com.sts.ssms.ui.helpdesk.photogallery.model

import com.sts.ssms.ui.search.model.SearchItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumUiModel(
    override val id: Int,
    val albumName: String,
    val albumDescription: String,
    val createdOn: String,
    val createdBy: String,
    val albumImageUrl: String,
    val albumImageName: String,
    val photos: List<PhotoUiModel> = ArrayList()
) : SearchItem