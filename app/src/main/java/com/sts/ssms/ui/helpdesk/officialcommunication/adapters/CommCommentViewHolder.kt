package com.sts.ssms.ui.helpdesk.officialcommunication.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.databinding.ItemCommunicationCommentBinding
import com.sts.ssms.utils.inflateDataBindingView

class CommCommentViewHolder(val binding: ItemCommunicationCommentBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) =
            CommCommentViewHolder(parent.inflateDataBindingView(R.layout.item_communication_comment))
    }
}