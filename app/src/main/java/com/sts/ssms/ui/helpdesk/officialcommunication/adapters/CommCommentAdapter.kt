package com.sts.ssms.ui.helpdesk.officialcommunication.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.data.helpdesk.communication.api.CommunicationResponse.Comment

class CommCommentAdapter(private val list: MutableList<Comment>) :
    RecyclerView.Adapter<CommCommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommCommentViewHolder =
        CommCommentViewHolder.create(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CommCommentViewHolder, position: Int) {
        holder.binding.comment = list[position]
    }

    fun submitList(newItems: List<Comment>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }

}