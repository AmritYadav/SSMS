package com.sts.ssms.ui.helpdesk.noticeboard

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice
import com.sts.ssms.utils.formatString
import kotlinx.android.synthetic.main.fragment_notice_details.*

class NoticeDetailsFragment : Fragment() {

    companion object {
        const val KEY_NOTICE_MODEL = "NOTICE_MODEL"
        fun newInstance(bundle: Bundle): NoticeDetailsFragment {
            return NoticeDetailsFragment().apply {
                arguments = bundle
            }
        }
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Prevent user interaction with the Android Back Button.
            // Allowing only Up Indicator to navigate back to the source screen
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.onBackPressedDispatcher?.addCallback(backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notice_details, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_notification)?.let {
            it.isVisible = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notice = arguments?.getParcelable<Notice>(KEY_NOTICE_MODEL)
        notice?.let {
            loadWebView(it.desc)
            initViews(it)
        }
    }

    private fun loadWebView(htmlString: String) {
        web_view.apply {
            loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null)
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun initViews(notice: Notice) {
        title.text = notice.title
        created_by.formatString(R.string.placeholder_by, notice.createdBy)
        created_on.formatString(R.string.placeholder_created_on, notice.createdOn)
        expire_date.formatString(R.string.placeholder_expire_date, notice.expiryDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback.remove()
    }
}
