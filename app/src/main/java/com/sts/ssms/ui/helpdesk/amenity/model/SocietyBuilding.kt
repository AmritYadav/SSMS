package com.sts.ssms.ui.helpdesk.amenity.model

import com.google.gson.annotations.SerializedName

data class SocietyBuilding(
    val id: String,
    @SerializedName("text") val name: String
) {
    override fun toString(): String {
        return name
    }
}