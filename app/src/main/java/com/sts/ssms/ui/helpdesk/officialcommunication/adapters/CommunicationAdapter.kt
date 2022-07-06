package com.sts.ssms.ui.helpdesk.officialcommunication.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.ui.helpdesk.officialcommunication.models.Communication

class CommunicationAdapter(
    private val list: MutableList<Communication>,
    private val callback: (comm: Communication) -> Unit
) :
    RecyclerView.Adapter<CommunicationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunicationViewHolder =
        CommunicationViewHolder.create(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CommunicationViewHolder, position: Int) {
        holder.bind(list[position], callback)
    }

    fun submitList(communications: List<Communication>) {
        list.clear()
        list.addAll(communications)
        notifyDataSetChanged()
    }
}