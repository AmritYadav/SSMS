package com.sts.ssms.data.helpdesk.notification.api

import com.google.gson.annotations.SerializedName
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.notification.model.SsmsNotification
import com.sts.ssms.utils.DrawerMenu
import java.security.Principal

fun NotificationApiResponse.toSsmsNotification(): SsmsNotification =
    SsmsNotification(
        total = (requestAmenity ?: 0) + (noticeBoard ?: 0) + (vendorManagement ?: 0) +
                (staffManagement ?: 0) + if (paymentDetails.isEmpty()) 0 else 1,
        notificationDetails = prepareNavigationList()
    )

private fun NotificationApiResponse.prepareNavigationList(): List<SsmsNotification.NotificationDetail> {
    val list = arrayListOf<SsmsNotification.NotificationDetail>()
    if (paymentDetails.isNotEmpty())
        list.add(
            prepareGetNotificationDetails("Billing", 1, R.id.nav_payment_details)
        )
    if (noticeBoard != null && noticeBoard > 0)
        list.add(
            prepareGetNotificationDetails("Notice", noticeBoard, R.id.nav_notice_board)
        )
    if (requestAmenity != null && requestAmenity > 0)
        list.add(
            prepareGetNotificationDetails("Amenity Request", requestAmenity, R.id.nav_amenity)
        )
    if (vendorManagement != null && vendorManagement > 0)
        list.add(
            prepareGetNotificationDetails("Vendor", vendorManagement, R.id.nav_vendor_management)
        )
    if (staffManagement != null && staffManagement > 0)
        list.add(
            prepareGetNotificationDetails("Staff", staffManagement, R.id.nav_staff_management)
        )
    return list
}

private fun NotificationApiResponse.prepareGetNotificationDetails(menu: String, notificationCount: Int, navItem: Int) =
    SsmsNotification.NotificationDetail(
        if (menu == "Billing") "<b>" + menu + " Notification(s)</b> :<br>" +
                "<font color=#757575> Principal Amount : " + paymentDetails[0].principalAmount +
                "<br>Interest Amount : " + paymentDetails[0].interestAmount + "" +
                "<br>Total Amount : " + paymentDetails[0].total+"</font>"
        else "Total <b>$menu</b> Notification(s):&#160;&#160;<b><font color=#ff1900>$notificationCount</font></b>",
        navItem
    )

data class NotificationApiResponse(
    @SerializedName(DrawerMenu.REQUEST_AMENITY) val requestAmenity: Int?,
    @SerializedName(DrawerMenu.NOTICE_BOARD) val noticeBoard: Int?,
    @SerializedName(DrawerMenu.PAYMENT_DETAILS) val paymentDetails: List<PaymentDetails>,
    @SerializedName(DrawerMenu.VENDOR_MANAGEMENT) val vendorManagement: Int?,
    @SerializedName(DrawerMenu.STAFF_MANAGEMENT) val staffManagement: Int?
)

data class PaymentDetails(
    @SerializedName("principal_amount") val principalAmount: Double,
    @SerializedName("interest_amount") val interestAmount: Double,
    @SerializedName("total") val total: Double
)