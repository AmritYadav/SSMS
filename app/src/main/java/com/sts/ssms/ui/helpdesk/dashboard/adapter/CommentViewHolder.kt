package com.sts.ssms.ui.helpdesk.dashboard.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.dashboard.model.Comment
import com.sts.ssms.utils.inflateView

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val user = itemView.findViewById<TextView>(R.id.user)
    private val userComment = itemView.findViewById<TextView>(R.id.comment)
    private val status = itemView.findViewById<TextView>(R.id.status)

    fun bind(comment: Comment) {
        user.text = comment.user
        userComment.text = comment.comment
        status.text = comment.status
    }

    companion object {
        fun create(parent: ViewGroup): CommentViewHolder =
            CommentViewHolder(parent.inflateView(R.layout.item_ticket_comment))
    }
}