package com.sts.ssms.data.helpdesk.documents.api


import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.helpdesk.documents.api.DocumentsApiResponse.DocumentsResponse
import com.sts.ssms.ui.helpdesk.documents.model.Documents

data class DocumentsApiResponse(
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("per_page") val perPage: String,
    val total: Int,
    @SerializedName("data") val documents: List<DocumentsResponse>
) {
    data class DocumentsResponse(
        val id: Int,
        @SerializedName("folder_type") val folderType: String,
        @SerializedName("category_id") val categoryId: Int,
        @SerializedName("category_name") val categoryName: String,
        val name: String,
        val description: String,
        @SerializedName("folder_id") val folderId: Int,
        val path: String,
        @SerializedName("user_id") val userId: Int,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("approval_status") val approvalStatus: String,
        @SerializedName("uploaded_by") val uploadedBy: String
    )
}

fun DocumentsResponse.toDocument() = Documents(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId,
    categoryName = categoryName,
    uploadedOn = createdAt,
    path = path,
    uploadedBy = uploadedBy,
    status = approvalStatus
)