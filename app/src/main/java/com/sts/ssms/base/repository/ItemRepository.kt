package com.sts.ssms.base.repository

import com.sts.ssms.ui.search.model.SearchItem

interface ItemRepository<T: SearchItem> {
    fun getItems(query: String, filterId: Int): Listing<T>
}