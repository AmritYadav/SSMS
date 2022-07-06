package com.sts.ssms.ui.helpdesk.profile.model

data class Profile(
    var firstName: String,
    var lastName: String,
    var email: String,
    var mobileNo: String,
    var dob: String,
    var aadhaarCard: String,
    var panCard: String,
    var voterId: String,
    var avatar: String?
)