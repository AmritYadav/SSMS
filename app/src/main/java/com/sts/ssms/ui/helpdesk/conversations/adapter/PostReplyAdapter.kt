package com.sts.ssms.ui.helpdesk.conversations.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.ui.helpdesk.conversations.model.PostLike
import com.sts.ssms.ui.helpdesk.conversations.model.post.PostReplyUiModel

class PostReplyAdapter(
    private val parentPostPosition: Int,
    private val postReplyList: List<PostReplyUiModel>,
    private val likeCallback: (postLike: PostLike) -> Unit
) :
    RecyclerView.Adapter<PostReplyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostReplyViewHolder {
        return PostReplyViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return postReplyList.size
    }

    override fun onBindViewHolder(holder: PostReplyViewHolder, position: Int) {
        holder.bind(parentPostPosition, postReplyList[position], likeCallback)
    }

}