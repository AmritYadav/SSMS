package com.sts.ssms.ui.helpdesk.conversations.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.conversations.model.PostLike
import com.sts.ssms.ui.helpdesk.conversations.model.post.PostUiModel
import com.sts.ssms.ui.helpdesk.conversations.model.reply.PostReplyRequest
import com.sts.ssms.utils.afterTextChanged
import com.sts.ssms.utils.inflateView
import com.sts.ssms.utils.visibleGone

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title = itemView.findViewById<TextView>(R.id.tv_conversation_title)
    private val text = itemView.findViewById<TextView>(R.id.tv_conversation_desc)
    private val commentBy = itemView.findViewById<TextView>(R.id.tv_conversation_comment_by)
    private val postLike = itemView.findViewById<ToggleButton>(R.id.toggle_post_like)
    private val totalLikes = itemView.findViewById<TextView>(R.id.tv_total_likes)
    private val totalReplies = itemView.findViewById<TextView>(R.id.tv_total_reply)
    private val etPostComment = itemView.findViewById<EditText>(R.id.et_post_reply_comment)
    private val btnPostReply = itemView.findViewById<Button>(R.id.btn_post_reply)
    private val cancelPostReply = itemView.findViewById<Button>(R.id.btn_post_cancel)
    private val grpReplies = itemView.findViewById<Group>(R.id.grp_post_replies)

    fun bind(
        postUiModel: PostUiModel,
        activePostId: (postId: String?) -> Unit,
        likeCallback: (postLike: PostLike) -> Unit,
        postReply: (replyRequest: PostReplyRequest, itemPosition: Int) -> Unit
    ) {
        bindItem(postUiModel)
        postLike.setOnClickListener {
            val postLike =
                PostLike(
                    postUiModel.postId,
                    !postUiModel.liked,
                    postUiModel.totalLikes.toInt(),
                    adapterPosition
                )
            likeCallback.invoke(postLike)
        }
        totalReplies.setOnClickListener {
            val visibility = grpReplies.isVisible
            if (visibility) {
                grpReplies.isVisible = !visibility
                activePostId.invoke(null)
            } else {
                grpReplies.isVisible = !visibility
                activePostId.invoke(postUiModel.postId)
            }
        }
        btnPostReply.setOnClickListener {
            val postReplyReq = PostReplyRequest(postUiModel.postId, etPostComment.text.toString())
            postReply.invoke(postReplyReq, adapterPosition)
            etPostComment.text.clear()
        }
        etPostComment.afterTextChanged {
            btnPostReply.isEnabled = checkCommentLength(it)
        }
        cancelPostReply.setOnClickListener {
            activePostId.invoke(null)
            grpReplies.visibleGone(false)
        }
        text.setOnClickListener {
            if (text.maxLines == 2) text.maxLines = 10
            else text.maxLines = 2
        }
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            return PostViewHolder(parent.inflateView(R.layout.item_conversation))
        }
    }

    private fun bindItem(postUiModel: PostUiModel) {
        title.text = postUiModel.title
        text.text = postUiModel.content
        totalLikes.text = postUiModel.totalLikes
        totalReplies.text = postUiModel.totalReplies
        commentBy.text = postUiModel.firstName
        postLike.isChecked = postUiModel.liked
    }

    private fun checkCommentLength(postComment: String): Boolean {
        return postComment.length > 4
    }
}