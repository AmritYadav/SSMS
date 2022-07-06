package com.sts.ssms.data.helpdesk.staff.api

import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.helpdesk.RatingRequest

data class StaffRatingRequest(
    @SerializedName("staff_id") override val id: Int,
    override val rating: Int,
    override val comment: String
) : RatingRequest