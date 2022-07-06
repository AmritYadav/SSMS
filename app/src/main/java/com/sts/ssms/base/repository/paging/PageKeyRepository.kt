package com.sts.ssms.base.repository.paging

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.sts.ssms.base.repository.ItemRepository
import com.sts.ssms.base.repository.Listing
import com.sts.ssms.ui.search.model.SearchItem

abstract class PageKeyRepository<T : SearchItem> : ItemRepository<T> {

    protected abstract fun getSourceFactory(query: String, filterId: Int): ItemDataSourceFactory<T>

    @MainThread
    override fun getItems(query: String, filterId: Int): Listing<T> {

        val sourceFactory = getSourceFactory(query, filterId)

        val livePagedList = LivePagedListBuilder(sourceFactory, 10).build()

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }

        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState,
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            }
        )
    }
}