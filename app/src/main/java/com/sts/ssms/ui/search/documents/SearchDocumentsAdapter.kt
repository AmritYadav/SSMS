package com.sts.ssms.ui.search.documents

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.ui.helpdesk.documents.adapter.DocumentsViewHolder
import com.sts.ssms.ui.helpdesk.documents.model.Documents


class SearchDocumentsAdapter(
    retryCallback: () -> Unit,
    private val callback: (docUrl: String) -> Unit
) : SearchAdapter<Documents>(retryCallback) {

    override val layoutID = R.layout.item_notice

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: Documents?) =
        with((holder as DocumentsViewHolder)) {
            bind(item, callback)
        }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        DocumentsViewHolder.create(parent)
}