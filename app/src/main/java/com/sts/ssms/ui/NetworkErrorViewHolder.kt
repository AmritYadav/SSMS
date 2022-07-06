package com.sts.ssms.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.utils.visibleGone

class NetworkErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val grpRetryContent = itemView.findViewById<Group>(R.id.grp_retry_content)
    private val retryTitle = itemView.findViewById<TextView>(R.id.retry_msg)
    private val retryButton = itemView.findViewById<Button>(R.id.retry_button)
    private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)

    fun bind(networkState: NetworkState?, callback: () -> Unit?) {
        retryButton.setOnClickListener { callback() }

        when (networkState) {
            NetworkState.error(networkState?.msg) -> {
                retryTitle.text = networkState.msg
                grpRetryContent.visibleGone(true)
                progressBar.visibleGone(false)
            }
            NetworkState.LOADING -> {
                grpRetryContent.visibleGone(false)
                progressBar.visibleGone(true)
            }
            else -> println("Items Loaded")
        }
    }

    companion object {
        fun create(parent: ViewGroup): NetworkErrorViewHolder {
            return NetworkErrorViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_network_error,
                    parent,
                    false
                )
            )
        }
    }
}