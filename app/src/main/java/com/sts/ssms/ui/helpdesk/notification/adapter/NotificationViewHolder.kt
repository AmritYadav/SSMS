package com.sts.ssms.ui.helpdesk.notification.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.notification.model.SsmsNotification.NotificationDetail
import com.sts.ssms.utils.inflateView

class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title = itemView.findViewById<TextView>(R.id.notification_title)

    fun bind(notification: NotificationDetail) {
        title.text = HtmlCompat.fromHtml(notification.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    companion object {
        fun create(parent: ViewGroup): NotificationViewHolder =
            NotificationViewHolder(parent.inflateView(R.layout.item_notification))
    }
}