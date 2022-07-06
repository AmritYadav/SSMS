package com.sts.ssms.data.helpdesk.photogallery.api.model

import com.google.gson.annotations.SerializedName
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel
import com.sts.ssms.ui.helpdesk.photogallery.model.PhotoUiModel

fun AlbumApiResponse.toAlbumUiModel(): AlbumUiModel = AlbumUiModel(
    id,
    name,
    description,
    createdAt,
    createdBy,
    "",
    "",
    photosList.map { it.toPhotoUiModel() }
)

fun PhotoResponse.toPhotoUiModel(): PhotoUiModel = PhotoUiModel(
    id,
    name,
    imgPath,
    createdAt,
    approvalStatus
)

data class AlbumApiResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("created_by") val createdBy: String,
    @SerializedName("photos") val photosList: List<PhotoResponse>
)

data class PhotoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("folder_type") val folderType: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("http_path") val imgPath: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("visible_to") val visibleTo: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("approval_status") val approvalStatus: String,
    @SerializedName("created_at") val createdAt: String
)