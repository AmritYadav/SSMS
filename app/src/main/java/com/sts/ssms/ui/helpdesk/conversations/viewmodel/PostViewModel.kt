package com.sts.ssms.ui.helpdesk.conversations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.conversations.repository.PostRepository
import com.sts.ssms.paging.conversation.PostDataSourceFactory
import com.sts.ssms.ui.helpdesk.conversations.model.PostLike
import com.sts.ssms.ui.helpdesk.conversations.model.PostLikeResult
import com.sts.ssms.ui.helpdesk.conversations.model.post.PostUiModel
import com.sts.ssms.ui.helpdesk.conversations.model.reply.PostReplyRequest
import com.sts.ssms.ui.helpdesk.conversations.model.reply.PostReplyResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val PAGE_SIZE = 6

class PostViewModel(
    private val postRepository: PostRepository,
    private val postDataSourceFactory: PostDataSourceFactory
) : ViewModel() {

    var postList: LiveData<PagedList<PostUiModel>>

    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null

    val loadNewConversation = MutableLiveData<Boolean>()

    private var _postActionState = MutableLiveData<NetworkState>()
    val postActionState: LiveData<NetworkState> = _postActionState

    private val _postLikeResult = MutableLiveData<PostLikeResult>()
    val postLikeResult: LiveData<PostLikeResult> = _postLikeResult

    private val _postReplyResult = MutableLiveData<PostReplyResult>()
    val postReplyResult: LiveData<PostReplyResult> = _postReplyResult

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()

        postList = LivePagedListBuilder<Int, PostUiModel>(postDataSourceFactory, config).build()
        networkState =
            Transformations.switchMap(postDataSourceFactory.postDataSourceLiveData) { it.getNetworkState() }
        refreshState =
            Transformations.switchMap(postDataSourceFactory.postDataSourceLiveData) { it.refreshState() }
    }

    fun retry() = postDataSourceFactory.postDataSourceLiveData.value?.retry()

    fun refreshAllData() = postDataSourceFactory.postDataSourceLiveData.value?.refresh()

    override fun onCleared() {
        super.onCleared()
        postDataSourceFactory.postDataSourceLiveData.value?.onCleared()
    }

    fun postUserLikeDislike(postLike: PostLike) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) { _postActionState.value = NetworkState.LOADING }
            val result = postRepository.postLikeUnLike(postLike.postId, postLike.liked)
            withContext(Dispatchers.Main) {
                if (result.success != null) {
                    postLike.totalLikes =
                        if (postLike.liked) ++postLike.totalLikes else --postLike.totalLikes
                    _postLikeResult.value =
                        PostLikeResult(postLike = postLike, success = result.success)
                    withContext(Dispatchers.Main) { _postActionState.value = NetworkState.LOADED }
                } else {
                    postLike.liked = !postLike.liked
                    _postLikeResult.value =
                        PostLikeResult(postLike = postLike, error = result.error)
                    withContext(Dispatchers.Main) {
                        _postActionState.value = NetworkState.error(result.error)
                    }
                }
            }
        }
    }

    fun postLoadNewReply(postReplyRequest: PostReplyRequest, adapterPos: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) { _postActionState.value = NetworkState.LOADING }
            val result = postRepository.postNewLike(postReplyRequest)
            withContext(Dispatchers.Main) {
                if (result.success != null) {
                    _postReplyResult.value =
                        PostReplyResult(
                            replyList = result.replyList,
                            success = result.success,
                            adapterPosition = adapterPos
                        )
                    withContext(Dispatchers.Main) { _postActionState.value = NetworkState.LOADED }
                } else {
                    _postReplyResult.value =
                        PostReplyResult(
                            error = result.error,
                            adapterPosition = adapterPos
                        )
                    withContext(Dispatchers.Main) {
                        _postActionState.value = NetworkState.error(result.error)
                    }
                }
            }
        }
    }
}