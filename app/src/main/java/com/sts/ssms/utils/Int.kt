package com.sts.ssms.utils

import android.graphics.Color

private const val DARK_GREY = 0xFF333333.toInt()

fun Int.getContrastColor(): Int {
    val y = (299 * Color.red(this) + 587 * Color.green(this) + 114 * Color.blue(this)) / 1000
    return if (y >= 149 && this != Color.BLACK) DARK_GREY else Color.WHITE
}

fun Int.adjustAlpha(factor: Float): Int {
    val alpha = Math.round(Color.alpha(this) * factor)
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}

fun Int.addBitIf(add: Boolean, bit: Int) =
    if (add) {
        addBit(bit)
    } else {
        removeBit(bit)
    }

fun Int.removeBit(bit: Int) = addBit(bit) - bit

fun Int.addBit(bit: Int) = this or bit