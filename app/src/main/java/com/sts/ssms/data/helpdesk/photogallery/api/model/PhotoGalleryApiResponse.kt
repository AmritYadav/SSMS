package com.sts.ssms.data.helpdesk.photogallery.api.model

import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.helpdesk.photogallery.api.model.PhotoGalleryApiResponse.*
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel
import java.util.ArrayList

fun AlbumResponse.toAlbumUiModel(): AlbumUiModel = AlbumUiModel(
    id,
    name,
    description,
    createdAt,
    createdBy,
    imageUrl ?: "",
    imageName ?: ""
)

data class PhotoGalleryApiResponse(
    @SerializedName("total") val totalPages: String,
    @SerializedName("per_page") val perPage: String,
    @SerializedName("current_page") val page: Int,
    @SerializedName("data") val albums: List<AlbumResponse> = ArrayList()
) {
    data class AlbumResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
        @SerializedName("user_id") val userId: Int,
        @SerializedName("society_id") val societyId: Int,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("status") val status: String,
        @SerializedName("created_by") val createdBy: String,
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("image_name") val imageName: String?
    )
}
