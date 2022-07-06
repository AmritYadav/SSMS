package com.sts.ssms.data.search.api

import com.sts.ssms.ui.search.model.SearchItem

data class SearchResult<T: SearchItem>(
    val itemList: List<T>? = null,
    val error: String? = null
)