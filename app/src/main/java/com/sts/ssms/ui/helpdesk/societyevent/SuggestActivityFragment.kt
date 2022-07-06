package com.sts.ssms.ui.helpdesk.societyevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.societyevent.viewmodel.SocietyEventViewModel
import com.sts.ssms.ui.helpdesk.societyevent.viewmodel.SuggestActivityViewModel
import com.sts.ssms.utils.afterTextChanged
import com.sts.ssms.utils.showDatePickerDialog
import com.sts.ssms.utils.showToast
import com.sts.ssms.utils.toStringEventDate
import kotlinx.android.synthetic.main.layout_suggest_society_event_content.*
import org.joda.time.LocalDate
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SuggestActivityFragment : BottomSheetDialogFragment() {

    private val suggestActivityViewModel by viewModel<SuggestActivityViewModel>()
    private var eventDate = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_suggest_society_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()
        suggest_event_date.text = eventDate.toStringEventDate()

        ti_et_suggest_event_title.afterTextChanged {
            suggestActivityViewModel.newPostFormState(it)
        }

        suggest_event_date.setOnClickListener {
            val context = activity ?: return@setOnClickListener
            (context as AppCompatActivity).showDatePickerDialog(eventDate) { date ->
                setEventDate(date)
            }
        }

        save_suggest_event.setOnClickListener {
            suggestActivityViewModel.postNewConversation(
                ti_et_suggest_event_title.text.toString(),
                eventDate.toString()
            )
        }
    }

    private fun setObserver() {
        suggestActivityViewModel.newPostFormState.observe(viewLifecycleOwner) {
            save_suggest_event.isEnabled = it.isDataValid
        }

        suggestActivityViewModel.postResult.observe(viewLifecycleOwner) {
            if (it.success != null) {
                postResult(it.success)
                dialog?.dismiss()
                requireParentFragment().getViewModel<SocietyEventViewModel>().refreshAllData()
            } else {
                postResult(it.error!!)
            }
        }
    }

    private fun postResult(message: String) {
        activity?.showToast(message)
    }

    private fun setEventDate(date: LocalDate) {
        eventDate = date
        suggest_event_date.text = date.toStringEventDate()
    }

}