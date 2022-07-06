package com.sts.ssms.ui.helpdesk.dashboard.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.dashboard.model.Ticket
import com.sts.ssms.utils.formatString
import com.sts.ssms.utils.inflateView

class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val ticketTitle = itemView.findViewById<TextView>(R.id.tv_dashboard_ticket)
    private val issue = itemView.findViewById<TextView>(R.id.tv_dashboard_issue)
    private val createdOn = itemView.findViewById<TextView>(R.id.tv_dashboard_created_on)
    private val flat = itemView.findViewById<TextView>(R.id.tv_dashboard_flat_no)
    private val status = itemView.findViewById<TextView>(R.id.tv_dashboard_status)

    fun bind(ticket: Ticket) {
        ticketTitle.text = ticket.categoryName
        issue.text = ticket.issue
        createdOn.formatString(R.string.placeholder_created_on, ticket.createdOn)
        flat.formatString(R.string.flat, ticket.flat)
        status.formatString(R.string.ticket_status, ticket.ticketStatus)
    }

    companion object {
        fun create(parent: ViewGroup): TicketViewHolder =
            TicketViewHolder(parent.inflateView(R.layout.item_dashboard_ticket))
    }
}