package com.sts.ssms.data.search.repository

import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.ui.search.model.SearchItem

interface SearchRepository<T : SearchItem> {
    suspend fun getItem(page: Int, query: String, filterId: Int): SearchResult<T>
}