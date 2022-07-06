package com.sts.ssms.custom.calendar.helpers

const val LOW_ALPHA = .3f
const val MEDIUM_ALPHA = .6f

const val DAY_CODE = "day_code"

const val FLAG_IS_PAST_EVENT = 2

fun getNowSeconds() = System.currentTimeMillis() / 1000L
