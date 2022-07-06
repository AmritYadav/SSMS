package com.sts.ssms.paging.conversation

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.data.helpdesk.conversations.repository.PostRepository
import com.sts.ssms.ui.helpdesk.conversations.model.post.PostUiModel

class PostDataSourceFactory(private val postRepository: PostRepository) :
    DataSource.Factory<Int, PostUiModel>() {

    val postDataSourceLiveData = MutableLiveData<PostDataSource>()

    override fun create(): DataSource<Int, PostUiModel> {
        val postDataSource = PostDataSource(postRepository)
        postDataSourceLiveData.postValue(postDataSource)
        return postDataSource
    }
}