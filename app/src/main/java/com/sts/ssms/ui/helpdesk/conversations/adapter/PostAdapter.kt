package com.sts.ssms.ui.helpdesk.conversations.adapter

import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.NetworkErrorViewHolder
import com.sts.ssms.ui.helpdesk.conversations.model.PostLike
import com.sts.ssms.ui.helpdesk.conversations.model.post.PostReplyUiModel
import com.sts.ssms.ui.helpdesk.conversations.model.post.PostUiModel
import com.sts.ssms.ui.helpdesk.conversations.model.reply.PostReplyRequest
import java.util.*

private const val VIEW_TYPE_DATA = 0x010
private const val VIEW_TYPE_RETRY = 0x011

class PostAdapter(
    private val callback: () -> Unit?,
    private val likeCallback: (postLike: PostLike) -> Unit,
    private val postReply: (replyRequest: PostReplyRequest, itemPosition: Int) -> Unit
) :
    PagedListAdapter<PostUiModel, RecyclerView.ViewHolder>(ConversationDiffUtils) {

    private var networkState: NetworkState? = null
    private val viewPool = RecyclerView.RecycledViewPool()

    private var activePostId: String? = null
    private val replyCallback: (postId: String?) -> Unit = { id ->
        activePostId = id
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> PostViewHolder.create(parent)
            VIEW_TYPE_RETRY -> NetworkErrorViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasNetworkErrorRow()) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> {
                val postUiModel = getItem(position)
                postUiModel?.let {
                    holder.bind(postUiModel, replyCallback, likeCallback, postReply)
                    val grp = holder.itemView.findViewById<Group>(R.id.grp_post_replies)
                    val rvPostReplies =
                        holder.itemView.findViewById<RecyclerView>(R.id.rv_conversation_reply)
                    rvPostReplies.apply {
                        val postReplyList = it.postReplyList ?: emptyList()
                        adapter = PostReplyAdapter(position, postReplyList, likeCallback)
                        setRecycledViewPool(viewPool)
                    }
                    grp.isVisible = activePostId != null && activePostId == it.postId
                }
            }
            is NetworkErrorViewHolder -> holder.bind(networkState, callback)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) VIEW_TYPE_DATA else VIEW_TYPE_RETRY
    }

    private fun hasNetworkErrorRow() =
        networkState != null && (networkState == NetworkState.LOADING || networkState == NetworkState.error(
            networkState?.msg
        ))

    fun updateNetworkState(newNetworkState: NetworkState?) {
        networkState = newNetworkState
        notifyItemChanged(super.getItemCount())
    }

    fun updatePostLikeStatus(postLike: PostLike, isSuccess: Boolean) {
        if (postLike.isPostLike) updatePostLikeDislikeStatus(postLike, isSuccess)
        else updateReplyLikeDislikeStatus(postLike, isSuccess)
    }

    private fun updatePostLikeDislikeStatus(postLike: PostLike, isSuccess: Boolean) {
        if (isSuccess) {
            val oldItem = getItem(postLike.postAdapterPosition)
            oldItem?.let {
                it.liked = postLike.liked
                it.totalLikes = postLike.totalLikes.toString()
            }
        }
        notifyItemChanged(postLike.postAdapterPosition)
    }

    private fun updateReplyLikeDislikeStatus(postLike: PostLike, isSuccess: Boolean) {
        if (isSuccess) {
            val oldItem = getItem(postLike.postAdapterPosition)
            oldItem?.let { postUiModel ->
                postUiModel.postReplyList?.let { postReplyList ->
                    postReplyList.find { it.postReplyId == postLike.postId }.apply {
                        this?.liked = postLike.liked
                        this?.totalLikes = postLike.totalLikes.toString()
                    }
                }
            }
        }
        notifyItemChanged(postLike.postAdapterPosition)
    }

    fun updatePostReplyList(postReplyList: List<PostReplyUiModel>, pos: Int) {
        val postItem = getItem(pos)
        postItem?.let {
            it.totalReplies = postReplyList.size.toString()
            it.postReplyList = postReplyList
        }
        notifyItemChanged(pos)
    }

    fun resetActivePostId() {
        activePostId = null
    }

}

object ConversationDiffUtils : DiffUtil.ItemCallback<PostUiModel>() {
    override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return Objects.equals(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
        return (oldItem.postId == newItem.postId) && oldItem.liked == newItem.liked
    }
}
