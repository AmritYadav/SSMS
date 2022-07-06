package com.sts.ssms.ui.helpdesk.myflat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.ui.helpdesk.myflat.model.MyFlatUiModel

class MyFlatAdapter(
    private val flatMembersList: ArrayList<MyFlatUiModel>,
    private val imageCallback: (imgUrl: String?) -> Unit
) :
    RecyclerView.Adapter<MyFlatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFlatViewHolder {
        return MyFlatViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return flatMembersList.size
    }

    override fun onBindViewHolder(holder: MyFlatViewHolder, position: Int) {
        val flatUiModel = flatMembersList[position]
        holder.bind(flatUiModel, imageCallback) {
            val isExpanded = flatUiModel.isExpanded
            flatUiModel.isExpanded = !isExpanded
            notifyItemChanged(position)
        }
    }

    fun submitList(flatOwnersList: List<MyFlatUiModel>) {
        this.flatMembersList.clear()
        this.flatMembersList.addAll(flatOwnersList)
        notifyDataSetChanged()
    }
}