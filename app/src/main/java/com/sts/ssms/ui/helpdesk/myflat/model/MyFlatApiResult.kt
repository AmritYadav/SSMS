package com.sts.ssms.ui.helpdesk.myflat.model

import com.sts.ssms.data.helpdesk.myflat.api.MyFlatResponse

data class MyFlatApiResult(
    val myFlatResponse: MyFlatResponse? = null,
    val error: String? = null
)