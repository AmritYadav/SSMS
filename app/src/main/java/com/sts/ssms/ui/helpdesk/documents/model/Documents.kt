package com.sts.ssms.ui.helpdesk.documents.model

import com.sts.ssms.ui.search.model.SearchItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Documents (
    override val id:Int,
    val name: String,
    val description: String,
    val categoryId: Int,
    val categoryName: String,
    val path: String,
    val uploadedOn: String,
    val uploadedBy: String,
    val status:String
):SearchItem