package com.sts.ssms.base.repository.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.ui.search.model.SearchItem

abstract class ItemDataSourceFactory<T : SearchItem> : DataSource.Factory<Int, T>() {

    private val _sourceLiveData = MutableLiveData<PageKeyedItemDataSource<T>>()
    val sourceLiveData: LiveData<PageKeyedItemDataSource<T>>
        get() = _sourceLiveData

    protected abstract fun getDataSource(): PageKeyedItemDataSource<T>

    override fun create(): DataSource<Int, T> {
        val source = getDataSource()
        _sourceLiveData.postValue(source)
        return source
    }
}