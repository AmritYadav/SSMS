package com.sts.ssms.ui.helpdesk.dashboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.ui.helpdesk.dashboard.model.Ticket

class DashboardTicketAdapter(
    private var ticketList: List<Ticket>,
    private val callback: (ticket: Ticket) -> Unit
) :
    RecyclerView.Adapter<TicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        return TicketViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = ticketList[position]
        holder.bind(ticket)
        holder.itemView.setOnClickListener { callback.invoke(ticket) }
    }

    fun submitList(tickets: List<Ticket>) {
        ticketList = tickets
        notifyDataSetChanged()
    }

}