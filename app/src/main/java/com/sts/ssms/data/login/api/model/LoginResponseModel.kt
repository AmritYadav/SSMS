package com.sts.ssms.data.login.api.model

import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.database.entity.SocietyEntity
import com.sts.ssms.data.database.entity.UserEntity

fun UserResponse.toUserEntity(): UserEntity {
    return UserEntity(
        id = 1,
        userId = this.userId,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        contactNo = this.contactNo,
        dob = "",
        aadhaarNo = "",
        panCardNo = "",
        voterId = "",
        imageUrl = avatar ?: ""
    )
}

fun SocietyResponse.toSociety(userId: String): SocietyEntity {
    return SocietyEntity(
        id = 0,
        userId = userId,
        societyId = this.id,
        name = this.name,
        address = this.address,
        isApproved = this.isApproved,
        roleName = this.roleName
    )
}

data class UserResponse(
    @SerializedName("user_id") val userId: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val avatar: String?,
    val email: String,
    @SerializedName("password_changed") val passwordChanged: Int,
    @SerializedName("contact_no") val contactNo: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("societies") val societyList: List<SocietyResponse>
)

data class SocietyResponse(
    val id: String,
    val name: String,
    val address: String,
    @SerializedName("is_approved") val isApproved: Boolean,
    @SerializedName("role_name") val roleName: String
)
