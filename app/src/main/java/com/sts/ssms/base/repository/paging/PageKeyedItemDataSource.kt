package com.sts.ssms.base.repository.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.sts.ssms.NetworkState
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.search.model.SearchItem
import kotlinx.coroutines.*

abstract class PageKeyedItemDataSource<T : SearchItem> : PageKeyedDataSource<Int, T>() {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _initialLoad = MutableLiveData<NetworkState>()
    val initialLoad: LiveData<NetworkState>
        get() = _initialLoad

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    abstract suspend fun fetchItem(page: Int): SearchResult<T>

    abstract fun getSearchRepository(): SearchRepository<T>

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        retry = {
            loadInitial(params, callback)
        }
        executeQuery(isInitialLoad = true, page = 1) { callback.onResult(it, null, 2) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        retry = {
            loadAfter(params, callback)
        }
        executeQuery(isInitialLoad = false, page = params.key + 1) {
            callback.onResult(
                it,
                params.key + 1
            )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
    }

    private fun executeQuery(
        isInitialLoad: Boolean,
        page: Int,
        callback: (List<T>) -> Unit
    ) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) {
                _networkState.postValue(NetworkState.LOADING)
                if (isInitialLoad) _initialLoad.postValue(NetworkState.LOADING)
            }
            val response = fetchItem(page)
            withContext(Dispatchers.Main) {
                if (response.itemList != null) {
                    callback(response.itemList)
                    retry = null
                    _networkState.postValue(NetworkState.LOADED)
                    if (isInitialLoad) _initialLoad.postValue(NetworkState.LOADED)
                } else {
                    _networkState.postValue(NetworkState.error(response.error))
                    if (isInitialLoad) _initialLoad.postValue(NetworkState.error(response.error))
                }
            }
        }
    }

    // Clear job when the linked activity is destroyed to avoid memory leaks
    fun onCleared() {
        completableJob.cancel()
    }

}