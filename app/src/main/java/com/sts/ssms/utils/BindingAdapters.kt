package com.sts.ssms.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import org.jetbrains.annotations.NotNull

@BindingAdapter(value = ["src", "circleCrop"], requireAll = false)
fun loadImage(@NotNull view: ImageView, src: String?, circleCrop: Boolean) {
    if (circleCrop) view.loadCircularImage(src)
    else view.loadOriginalImage(src)
}