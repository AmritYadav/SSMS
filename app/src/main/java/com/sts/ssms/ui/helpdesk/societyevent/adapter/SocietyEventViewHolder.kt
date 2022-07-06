package com.sts.ssms.ui.helpdesk.societyevent.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.databinding.ItemSocietyEventBinding
import com.sts.ssms.ui.helpdesk.societyevent.model.SocietyEvent
import com.sts.ssms.utils.inflateDataBindingView

class SocietyEventViewHolder(private val binding: ItemSocietyEventBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        societyEvent: SocietyEvent?
    ) {
        societyEvent?.let { binding.societyEvent = it }
    }

    companion object {
        fun create(parent: ViewGroup): SocietyEventViewHolder = SocietyEventViewHolder(
            parent.inflateDataBindingView(R.layout.item_society_event)
        )
    }
}