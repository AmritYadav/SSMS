package com.sts.ssms.ui.helpdesk.dashboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.ui.helpdesk.dashboard.model.Comment

class CommentAdapter(private var comments: List<Comment>) :
    RecyclerView.Adapter<CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.create(parent)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    fun submitList(list: List<Comment>) {
        comments = list
        notifyDataSetChanged()
    }
}