package com.sts.ssms.paging.payments

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.data.helpdesk.payments.repository.PaymentRepository
import com.sts.ssms.ui.helpdesk.paymentdetails.model.PaymentUiModel

class PaymentDataSourceFactory(private val paymentRepository: PaymentRepository) : DataSource.Factory<Int, PaymentUiModel>() {

    val paymentDataSourceLiveData = MutableLiveData<PaymentDataSource>()

    override fun create(): DataSource<Int, PaymentUiModel> {
        val paymentDataSource = PaymentDataSource(paymentRepository)
        paymentDataSourceLiveData.postValue(paymentDataSource)
        return paymentDataSource
    }
}