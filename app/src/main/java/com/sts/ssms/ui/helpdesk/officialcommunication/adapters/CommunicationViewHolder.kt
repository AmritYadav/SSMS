package com.sts.ssms.ui.helpdesk.officialcommunication.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.databinding.ItemCommunicationBinding
import com.sts.ssms.ui.helpdesk.officialcommunication.models.Communication
import com.sts.ssms.utils.inflateDataBindingView

class CommunicationViewHolder(val binding: ItemCommunicationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(comm: Communication, callback: (comm: Communication) -> Unit) {
        binding.communication = comm
        itemView.setOnClickListener { callback.invoke(comm) }
    }

    companion object {
        fun create(parent: ViewGroup) =
            CommunicationViewHolder(parent.inflateDataBindingView(R.layout.item_communication))
    }
}