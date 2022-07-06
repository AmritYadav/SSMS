package com.sts.ssms.ui.helpdesk.conversations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.conversations.adapter.PostAdapter
import com.sts.ssms.ui.helpdesk.conversations.model.PostLike
import com.sts.ssms.ui.helpdesk.conversations.model.reply.PostReplyRequest
import com.sts.ssms.ui.helpdesk.conversations.viewmodel.PostViewModel
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.fragment_post.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostListFragment : Fragment() {

    private lateinit var postAdapter: PostAdapter
    private val postViewModel by viewModel<PostViewModel>()
    private lateinit var fm: FragmentManager

    private val likeCallback: (postLike: PostLike) -> Unit = {
        postViewModel.postUserLikeDislike(it)
    }

    private val replyCallback: (replyRequest: PostReplyRequest, itemPosition: Int) -> Unit =
        { request, pos ->
            postViewModel.postLoadNewReply(request, pos)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fm = childFragmentManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return container?.inflateView(R.layout.fragment_post)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecycler()
        setupObservables()
        configureSwipeRefresh()
        fab_new_conversation.setOnClickListener {
            NewPostFragment().apply {
                show(fm, NewPostFragment::javaClass.name)
            }
        }
    }

    private fun configureRecycler() {
        postAdapter = PostAdapter({ postViewModel.retry() }, likeCallback, replyCallback)
        rv_conversations.apply {
            adapter = postAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) fab_new_conversation.hide()
                    if (dy < 0) fab_new_conversation.show()
                }
            })
        }
    }

    private fun setupObservables() {
        postViewModel.networkState?.observe(viewLifecycleOwner) { it ->
            postAdapter.updateNetworkState(it)
            postViewModel.postList.value?.let {
                view_empty_state.updateEmptyState(it.isEmpty(), getString(R.string.prompt_empty_conversation))
            }
        }
        postViewModel.postList.observe(viewLifecycleOwner) {
            rv_conversations.scrollToPosition(0)
            postAdapter.submitList(it)
        }
        postViewModel.loadNewConversation.observe(viewLifecycleOwner) {
            if (it) {
                postViewModel.refreshAllData()
                postAdapter.resetActivePostId()
            }
        }
        postViewModel.postActionState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> {
                    activity?.window.disableViewInteraction()
                    pb_post_loader.visible(activity?.window)
                }
                else -> {
                    activity?.window.enableViewInteraction()
                    pb_post_loader.gone(activity?.window)
                }
            }
        }
        postViewModel.postLikeResult.observe(viewLifecycleOwner) { result ->
            result.postLike?.let {
                val isSuccess = result.success != null
                postAdapter.updatePostLikeStatus(it, isSuccess)
                if (result.error != null) activity?.showToast(result.error)
            }
        }
        postViewModel.postReplyResult.observe(viewLifecycleOwner) { result ->
            result.replyList?.let {
                postAdapter.updatePostReplyList(it, result.adapterPosition)
            }
            if (result.error != null) activity?.showToast(result.error)
        }
    }

    private fun configureSwipeRefresh() {
        var isSwipeEnabled = false
        swipe_refresh.apply {

            // Observing the NetworkState, to load fresh data or show retry button
            postViewModel.refreshState?.observe(viewLifecycleOwner) {
                if (isSwipeEnabled) {
                    isRefreshing = it == NetworkState.LOADING
                    isSwipeEnabled = isRefreshing
                }
            }

            setOnRefreshListener {
                isSwipeEnabled = true
                postViewModel.refreshAllData()
                postAdapter.resetActivePostId()
            }
        }
    }

}
