package com.sts.ssms.ui.search.vendor

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.ui.helpdesk.vendor.adapter.VendorViewHolder
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel

class SearchVendorAdapter(
    private val searchItemClickCallback: SearchItemClickCallback<VendorUiModel>,
    retryCallback: () -> Unit
) : SearchAdapter<VendorUiModel>(retryCallback) {

    override val layoutID: Int = R.layout.item_vendor_staff

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: VendorUiModel?) {
        with((holder as VendorViewHolder)) {
            bind(item, searchItemClickCallback)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        VendorViewHolder.create(parent)
}