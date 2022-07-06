package com.sts.ssms.data.helpdesk.profile.api

import com.sts.ssms.data.database.entity.UserEntity
import com.sts.ssms.ui.helpdesk.profile.model.Profile

data class ProfileResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val mobileNo: String?,
    val dob: String?,
    val aadharCardNumber: String?,
    val panCardNumber: String?,
    val voterId: String?,
    val imageUrl: String?
)

fun ProfileResponse.toUserEntity(): UserEntity {
    return UserEntity(
        id = 1,
        userId = id,
        firstName = firstName,
        lastName = lastName,
        contactNo = mobileNo ?: "",
        email = email,
        dob = dob ?: "",
        aadhaarNo = aadharCardNumber ?: "",
        panCardNo = panCardNumber ?: "",
        voterId = voterId ?: "",
        imageUrl = imageUrl ?: ""
    )
}

fun UserEntity.toProfile(): Profile {
    return Profile(
        firstName = firstName,
        lastName = lastName,
        email = email,
        dob = dob?.setDefaultIfEmpty() ?: DEFAULT_VALUE,
        voterId = voterId.setDefaultIfEmpty(),
        avatar = imageUrl,
        aadhaarCard = aadhaarNo.setDefaultIfEmpty(),
        panCard = panCardNo.setDefaultIfEmpty(),
        mobileNo = contactNo
    )
}

fun String.setDefaultIfEmpty(): String {
    return if (this.isEmpty()) DEFAULT_VALUE else this
}

private const val DEFAULT_VALUE = "---"