package com.sts.ssms.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sts.ssms.R

@Entity(tableName = "menu", indices = [Index(value = ["society_id"], unique = true)])
data class MenuEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "society_id") val societyId: String,
    val dashboard: Boolean,
    val conversations: Boolean,
    @ColumnInfo(name = "my_flat") val myFlat: Boolean,
    @ColumnInfo(name = "request_amenity") val requestAmenity: Boolean,
    val documents: Boolean,
    @ColumnInfo(name = "photo_gallery") val photoGallery: Boolean,
    @ColumnInfo(name = "notice_board") val noticeBoard: Boolean,
    @ColumnInfo(name = "society_events") val societyEvents: Boolean,
    @ColumnInfo(name = "official_communication") val officialCommunication: Boolean,
    @ColumnInfo(name = "payment_details") val paymentDetails: Boolean,
    @ColumnInfo(name = "vendor_management") val vendorManagement: Boolean,
    @ColumnInfo(name = "staff_management") val staffManagement: Boolean
)

fun MenuEntity?.toMenu(): List<Int> {
    val menuItems = defaultMenus()
    this?.let {
        if (dashboard) menuItems.add(R.id.nav_dashboard)
        if (conversations) menuItems.add(R.id.nav_conversations)
        if (myFlat) menuItems.add(R.id.nav_my_flat)
        if (requestAmenity) menuItems.add(R.id.nav_amenity)
        if (documents) menuItems.add(R.id.nav_documents)
        if (photoGallery) menuItems.add(R.id.nav_gallery)
        if (noticeBoard) menuItems.add(R.id.nav_notice_board)
        if (societyEvents) menuItems.add(R.id.nav_society_events)
        if (officialCommunication) menuItems.add(R.id.nav_official_communication)
        if (paymentDetails) menuItems.add(R.id.nav_payment_details)
        if (vendorManagement) menuItems.add(R.id.nav_vendor_management)
        if (staffManagement) menuItems.add(R.id.nav_staff_management)
    }
    return menuItems
}

private fun defaultMenus(): ArrayList<Int> {
    val menuItems = ArrayList<Int>()
    menuItems.add(R.id.nav_profile)
    menuItems.add(R.id.nav_bye_laws)
    menuItems.add(R.id.nav_features)
    return menuItems
}