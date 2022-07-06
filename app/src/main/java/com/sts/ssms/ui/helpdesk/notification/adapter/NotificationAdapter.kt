package com.sts.ssms.ui.helpdesk.notification.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.ui.helpdesk.notification.model.SsmsNotification.NotificationDetail

class NotificationAdapter(
    private val notifications: List<NotificationDetail>,
    val callback: (navItem: Int) -> Unit
) :
    RecyclerView.Adapter<NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder =
        NotificationViewHolder.create(parent)

    override fun getItemCount(): Int = notifications.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notificationDetail = notifications[position]
        holder.bind(notificationDetail)
        holder.itemView.setOnClickListener { callback.invoke(notificationDetail.navItem) }
    }

}