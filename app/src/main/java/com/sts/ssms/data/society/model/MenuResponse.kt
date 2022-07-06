package com.sts.ssms.data.society.model

import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.database.entity.FlatEntity
import com.sts.ssms.data.database.entity.MenuEntity
import com.sts.ssms.utils.DrawerMenu

data class MenuResponse(
    @SerializedName("menus") val menu: MenuItem?,
    @SerializedName("flats") val flat: List<FlatItem>
)

data class MenuItem(
    @SerializedName(DrawerMenu.CONVERSATION) val conversation: String?,
    @SerializedName(DrawerMenu.DOCUMENTS) val document: String?,
    @SerializedName(DrawerMenu.SOCIETY_EVENTS) val event: String?,
    @SerializedName(DrawerMenu.HELPDESK) val helpDesk: String?,
    @SerializedName(DrawerMenu.MY_FLAT) val myFlat: String?,
    @SerializedName(DrawerMenu.NOTICE_BOARD) val notice: String?,
    @SerializedName(DrawerMenu.OFFICIAL_COMMUNICATION) val officialCommunication: String?,
    @SerializedName(DrawerMenu.PAYMENT_DETAILS) val paymentDetail: String?,
    @SerializedName(DrawerMenu.PHOTO_GALLERY) val photoGallery: String?,
    @SerializedName(DrawerMenu.REQUEST_AMENITY) val requestAmenity: String?,
    @SerializedName(DrawerMenu.VENDOR_MANAGEMENT) val vendor: String?,
    @SerializedName(DrawerMenu.STAFF_MANAGEMENT) val staff: String?
)

data class FlatItem(
    @SerializedName("building_name") val buildingName: String,
    @SerializedName("flat_id") val flatId: String,
    @SerializedName("flat_no") val flatNo: String,
    @SerializedName("relation") val relation: String,
    @SerializedName("block_id") val blockId: String?,
    @SerializedName("block") val block: String?
)

fun MenuItem?.toMenuEntity(societyId: String): MenuEntity {
    return this?.mapToMenuEntity(societyId) ?: defaultMenuEntity(societyId)
}

/**
 * Disabled Menus when MenuItem is null
 */
private fun defaultMenuEntity(societyId: String): MenuEntity = MenuEntity(
    id = 0,
    societyId = societyId,
    conversations = false,
    dashboard = false,
    documents = false,
    myFlat = false,
    noticeBoard = false,
    officialCommunication = false,
    paymentDetails = false,
    photoGallery = false,
    requestAmenity = false,
    societyEvents = false,
    staffManagement = false,
    vendorManagement = false
)

private fun MenuItem.mapToMenuEntity(societyId: String): MenuEntity = MenuEntity(
    id = 0,
    societyId = societyId,
    conversations = !conversation.isNullOrEmpty(),
    dashboard = !helpDesk.isNullOrEmpty(),
    documents = !document.isNullOrEmpty(),
    myFlat = !myFlat.isNullOrEmpty(),
    noticeBoard = !notice.isNullOrEmpty(),
    officialCommunication = !officialCommunication.isNullOrEmpty(),
    paymentDetails = !paymentDetail.isNullOrEmpty(),
    photoGallery = !photoGallery.isNullOrEmpty(),
    requestAmenity = !requestAmenity.isNullOrEmpty(),
    societyEvents = !event.isNullOrEmpty(),
    staffManagement = !staff.isNullOrEmpty(),
    vendorManagement = !vendor.isNullOrEmpty()
)

fun FlatItem.toFlatEntity(societyId: String): FlatEntity {
    return FlatEntity(
        id = 0,
        societyId = societyId,
        flatId = flatId,
        flatNo = flatNo,
        block = block ?: "",
        blockId = blockId ?: "",
        buildingName = buildingName,
        relation = relation
    )
}