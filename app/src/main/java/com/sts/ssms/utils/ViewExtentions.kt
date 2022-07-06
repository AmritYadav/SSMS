package com.sts.ssms.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sts.ssms.R
import kotlinx.android.synthetic.main.layout_empty_state.view.*

/**
 * To be used with ProgressBar
 *
 * sets visibility state of the view to View.VISIBLE and
 * also disables user interaction while loading data
 */
fun View.visible(window: Window?) {
    this.visibility = View.VISIBLE
    window.disableViewInteraction()
}

/**
 * To be used with ProgressBar
 *
 * sets visibility state of the view to View.GONE and
 * also enables user interaction when data loading is complete
 */
fun View.gone(window: Window?) {
    this.visibility = View.GONE
    window.enableViewInteraction()
}

fun View.visibleGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 * Inflate a [View] with given layoutId and attach it to the calling [ViewGroup].
 * @param layout Id of the layout to inflate.
 */
fun ViewGroup.inflateView(@LayoutRes layout: Int): View {
    return LayoutInflater.from(this.context).inflate(layout, this, false)
}

fun <T : ViewDataBinding> ViewGroup.inflateDataBindingView(@LayoutRes layout: Int): T =
    DataBindingUtil.inflate(LayoutInflater.from(this.context), layout, this, false)

fun ImageView.loadCircularImage(imgUrl: String?) {
    Glide.with(this)
        .asBitmap()
        .load(imgUrl)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .circleCrop()
        .placeholder(R.drawable.ic_profile_placeholder)
        .error(R.drawable.ic_profile_placeholder)
        .into(this)
}

fun ImageView.loadOriginalImage(imgUrl: String?) {
    Glide.with(this)
        .asBitmap()
        .load(imgUrl)
        .placeholder(R.drawable.placeholder_album)
        .error(R.drawable.placeholder_album)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun Window?.disableViewInteraction() {
    this?.let {
        setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}

fun Window?.enableViewInteraction() {
    this?.let { clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE) }
}

fun TextView.formatString(stringRes: Int, value: String = "") {
    return this.setText(
        HtmlCompat.fromHtml(
            this.context.getString(stringRes, value),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    )
}

fun View.updateEmptyState(isEmpty: Boolean, message: String){
    this.textView.text = message
    this.visibleGone(isEmpty)
 }