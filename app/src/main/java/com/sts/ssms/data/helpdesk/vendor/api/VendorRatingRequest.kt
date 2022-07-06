package com.sts.ssms.data.helpdesk.vendor.api

import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.helpdesk.RatingRequest

data class VendorRatingRequest(
    @SerializedName("vendor_id") override val id: Int,
    override val rating: Int,
    override val comment: String
) : RatingRequest