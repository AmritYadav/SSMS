package com.sts.ssms.custom.calendar.common

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.duolingo.open.rtlviewpager.RtlViewPager

class MyViewPager : RtlViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false // disable swipe
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return false // disable swipe
    }
}