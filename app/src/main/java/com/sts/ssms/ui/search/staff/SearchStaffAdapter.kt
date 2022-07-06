package com.sts.ssms.ui.search.staff

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.ui.helpdesk.staff.adapter.StaffViewHolder
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel

class SearchStaffAdapter(
    private val searchItemClickCallback: SearchItemClickCallback<StaffUiModel>,
    retryCallback: () -> Unit
) : SearchAdapter<StaffUiModel>(retryCallback) {

    override val layoutID: Int = R.layout.item_vendor_staff

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: StaffUiModel?) {
        with((holder as StaffViewHolder)) {
            bind(item, searchItemClickCallback)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        StaffViewHolder.create(parent)

}