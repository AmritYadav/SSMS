package com.sts.ssms.ui.helpdesk.bylaws

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.utils.gone
import com.sts.ssms.utils.showToast
import com.sts.ssms.utils.visible
import kotlinx.android.synthetic.main.fragment_by_laws.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ByLawsFragment : Fragment() {

    private val viewModel by viewModel<ByLawsViewModel>()
    private var fileName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_by_laws, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setObserver()
    }

    private fun setObserver() {
        viewModel.downloadState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> progress_bye_laws.visible(activity?.window)
                else -> progress_bye_laws.gone(activity?.window)
            }
        }
        viewModel.documentDownloadResult.observe(viewLifecycleOwner) {
            it.success?.let { res -> saveFile(res) }
            it.error?.let { res -> updateUserView(res) }
        }
    }

    private fun saveFile(inputStream: InputStream) {
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

    private fun setListener() {
        btn_bylaw_1.setOnClickListener {
            viewModel.byLawsForm(1)
            fileName = (it as Button).text.toString() + ".pdf"
        }
        btn_bylaw_2.setOnClickListener {
            viewModel.byLawsForm(2)
            fileName = (it as Button).text.toString() + ".pdf"
        }
    }

}
