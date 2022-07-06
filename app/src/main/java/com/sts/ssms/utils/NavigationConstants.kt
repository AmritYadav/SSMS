package com.sts.ssms.utils

import android.app.Activity
import com.sts.ssms.ui.helpdesk.HelpDeskActivity
import com.sts.ssms.ui.login.LoginActivity

const val NAVIGATE_TO_LOGIN = 1
const val NAVIGATE_TO_HELP_DESK = 2

fun getActivity(pageId: Int): Activity {
    return when (pageId) {
        NAVIGATE_TO_LOGIN -> LoginActivity()
        NAVIGATE_TO_HELP_DESK -> HelpDeskActivity()
        else -> TODO("add DefaultActivity")
    }
}