package com.sts.ssms.base.ui

import android.os.Parcelable

interface SearchItemClickCallback<T : Parcelable> {
    fun onClick(item: T)
}