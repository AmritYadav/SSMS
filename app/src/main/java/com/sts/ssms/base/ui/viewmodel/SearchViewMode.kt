package com.sts.ssms.base.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.base.repository.Listing
import com.sts.ssms.ui.search.model.SearchItem

abstract class SearchViewModel<T: SearchItem>: ViewModel() {

    protected abstract val repoResult: LiveData<Listing<T>>

    protected var filterId: Int = 0

    private val _query = MutableLiveData<String>()
    protected val query: LiveData<String>
        get() = _query

    val items: LiveData<PagedList<T>> by lazy { Transformations.switchMap(repoResult) { it.pagedList } }
    val networkState: LiveData<NetworkState> by lazy { Transformations.switchMap(repoResult) { it.networkState } }
    val refreshState: LiveData<NetworkState> by lazy { Transformations.switchMap(repoResult) { it.refreshState } }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun showQuery(query: String): Boolean {
        if (_query.value == query) {
            return false
        }
        _query.value = query
        return true
    }

    fun setSearchFilterId(filterId: Int) {
        this.filterId = filterId
    }

    fun retry() {
        repoResult.value?.retry?.invoke()
    }

}