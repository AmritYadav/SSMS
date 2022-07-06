package com.sts.ssms.ui.helpdesk.conversations.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.conversations.model.PostLike
import com.sts.ssms.ui.helpdesk.conversations.model.post.PostReplyUiModel
import com.sts.ssms.utils.inflateView

class PostReplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val commentBy = itemView.findViewById<TextView>(R.id.tv_post_reply_name)
    private val comment = itemView.findViewById<TextView>(R.id.tv_post_reply_comment)
    private val toggleLike = itemView.findViewById<ToggleButton>(R.id.toggle_post_like)
    private val totalLikes = itemView.findViewById<TextView>(R.id.tv_total_likes)

    fun bind(
        parentPostPosition: Int,
        postReplyUiModel: PostReplyUiModel?,
        likeCallback: (postLike: PostLike) -> Unit
    ) {
        postReplyUiModel?.let { postReply ->
            commentBy.text = postReply.firstName
            comment.text = postReply.desc
            toggleLike.isChecked = postReply.liked
            totalLikes.text = postReply.totalLikes
            toggleLike.setOnClickListener {
                val postLike =
                    PostLike(
                        postReply.postReplyId,
                        !postReply.liked,
                        postReply.totalLikes.toInt(),
                        postAdapterPosition = parentPostPosition,
                        isPostLike = false
                    )
                likeCallback.invoke(postLike)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): PostReplyViewHolder {
            return PostReplyViewHolder(parent.inflateView(R.layout.item_post_reply))
        }
    }
}