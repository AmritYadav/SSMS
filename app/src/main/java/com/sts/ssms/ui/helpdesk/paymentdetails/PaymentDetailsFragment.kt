package com.sts.ssms.ui.helpdesk.paymentdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.paymentdetails.adapter.PaymentDetailsAdapter
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.fragment_payment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class PaymentDetailsFragment : Fragment() {

    private val paymentViewModel by viewModel<PaymentViewModel>()

    private lateinit var paymentDetailsAdapter: PaymentDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return container?.inflateView(R.layout.fragment_payment_details)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecycler()
        setupObservables()
    }

    private fun configureRecycler() {
        paymentDetailsAdapter =
            PaymentDetailsAdapter(
                { paymentViewModel.retry() },
                { id -> paymentViewModel.downloadBill(id) })
        rv_payment_details.apply {
            adapter = paymentDetailsAdapter
        }
    }

    private fun setupObservables() {
        paymentViewModel.networkState?.observe(viewLifecycleOwner) { it ->
            paymentDetailsAdapter.updateNetworkState(it)
            paymentViewModel.paymentList.value?.let {
                view_empty_state.updateEmptyState(it.isEmpty(), getString(R.string.prompt_empty_payment))
            }
        }
        paymentViewModel.paymentList.observe(viewLifecycleOwner) {
            paymentDetailsAdapter.submitList(it)
        }
        paymentViewModel.downloadState?.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> pb_bill_download_loader.visible(activity?.window)
                else -> pb_bill_download_loader.gone(activity?.window)
            }
        }
        paymentViewModel.billResult?.observe(viewLifecycleOwner) {
            it.success?.let { res -> saveFile(res) }
            it.error?.let { res -> updateUserView(res) }
        }
    }

    private fun saveFile(inputStream: InputStream) {
        val fileName = "SSMS Bill.pdf"
        val file = File(
            activity?.getExternalFilesDir(null),
            fileName
        )
        val fileOutPutStream = FileOutputStream(file)
        val buff = ByteArray(inputStream.available())
        try {
            inputStream.buffered().use {
                while (true) {
                    val sz = it.read(buff)
                    if (sz <= 0) break
                    fileOutPutStream.write(buff)
                    fileOutPutStream.close()
                }
                fileOutPutStream.flush()
                updateUserView("File saved successfully. Location -> $file")
            }
        } catch (e: Exception) {
            updateUserView("Error saving File. Please try again.")
        } finally {
            inputStream.close()
        }
    }

    private fun updateUserView(error: String) {
        activity?.showToast(error)
    }
}
