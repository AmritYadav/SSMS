package com.sts.ssms.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sts.ssms.data.society.model.Society

@Entity(tableName = "society", indices = [Index(value = ["society_id"], unique = true)])
data class SocietyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "society_id") val societyId: String,
    val name: String,
    val address: String,
    @ColumnInfo(name = "is_approved") val isApproved: Boolean,
    @ColumnInfo(name = "role_name") val roleName: String
)

fun SocietyEntity.toSociety(): Society {
    return Society(id, societyId, name, roleName)
}