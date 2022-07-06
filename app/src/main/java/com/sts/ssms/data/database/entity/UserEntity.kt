package com.sts.ssms.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    val email: String,
    @ColumnInfo(name = "contact_no") val contactNo: String,
    val dob: String?,
    @ColumnInfo(name = "aadhaar_no") val aadhaarNo: String,
    @ColumnInfo(name = "pan_card_no") val panCardNo: String,
    @ColumnInfo(name = "voter_id") val voterId: String,
    @ColumnInfo(name = "image_url") val imageUrl: String
)