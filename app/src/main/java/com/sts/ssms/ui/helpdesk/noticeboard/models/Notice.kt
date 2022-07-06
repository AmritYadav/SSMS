package com.sts.ssms.ui.helpdesk.noticeboard.models

import com.sts.ssms.ui.search.model.SearchItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notice(
    override val id: Int,
    val title: String,
    val desc: String,
    val typeId: Int,
    val typeName: String,
    val createdBy: String,
    val createdOn: String,
    val expiryDate: String
) : SearchItem