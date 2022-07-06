package com.sts.ssms.ui.helpdesk.paymentdetails.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.paymentdetails.model.PaymentUiModel
import com.sts.ssms.utils.formatString
import com.sts.ssms.utils.inflateView
import com.sts.ssms.utils.visibleGone

class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val container = itemView.findViewById<CardView>(R.id.container_payment)
    private val flatBlockBuilding = itemView.findViewById<TextView>(R.id.tv_payment_flat_name)
    private val downloadBill = itemView.findViewById<ImageButton>(R.id.ib_payment_download_bill)
    private val paymentDetailsContent = itemView.findViewById<View>(R.id.payment_details_container)

    private val bill = itemView.findViewById<TextView>(R.id.tv_payment_bill)
    private val billDate = itemView.findViewById<TextView>(R.id.tv_payment_bill_date_val)
    private val dueDate = itemView.findViewById<TextView>(R.id.tv_payment_due_date_val)
    private val paymentStatus = itemView.findViewById<TextView>(R.id.tv_payment_status)
    private val paymentDate = itemView.findViewById<TextView>(R.id.tv_payment_date_val)
    private val paymentMode = itemView.findViewById<TextView>(R.id.tv_payment_mode_val)
    private val instrumentNo = itemView.findViewById<TextView>(R.id.tv_payment_instrument_no_val)

    fun bind(
        paymentUiModel: PaymentUiModel?,
        downloadCallback: (paymentId: Int) -> Unit,
        callback: ()-> Unit
    ) {
        paymentUiModel?.let {
            flatBlockBuilding.text = it.wingFlatBuilding
            bill.formatString(R.string.payment_bill, it.billAmount)
            billDate.text = it.billDate
            paymentStatus.text = it.paymentStatus
            dueDate.text = it.billDueDate
            paymentDate.text = it.paidDate
            paymentMode.text = it.paymentMode
            instrumentNo.text = it.chequeNumber
            downloadBill.visibleGone(it.enableDownload)
            paymentDetailsContent.visibleGone(it.isExpanded)
        }
        container.setOnClickListener {
            callback.invoke()
        }
        downloadBill.setOnClickListener { v ->
            if (v.isEnabled) paymentUiModel?.let {
                downloadCallback.invoke(it.id)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): PaymentViewHolder {
            return PaymentViewHolder(parent.inflateView(R.layout.item_payment))
        }
    }
}