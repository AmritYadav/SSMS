package com.sts.ssms.ui.helpdesk.dashboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R

import com.sts.ssms.databinding.FragmentDashboardDetailBinding
import com.sts.ssms.ui.helpdesk.dashboard.adapter.CommentAdapter
import com.sts.ssms.ui.helpdesk.dashboard.model.Ticket
import com.sts.ssms.ui.helpdesk.dashboard.viewmodel.DashboardDetailsViewModel
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.layout_ticket_details_comment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardDetailFragment : Fragment() {

    private val viewModel by viewModel<DashboardDetailsViewModel>()
    private lateinit var binding: FragmentDashboardDetailBinding
    private lateinit var commentAdapter: CommentAdapter

    companion object {
        const val KEY_TICKET = "TICKET"
        fun newInstance(ticket: Ticket): DashboardDetailFragment = DashboardDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_TICKET, ticket)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_notification)?.let {
            it.isVisible = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ticket = arguments?.getParcelable<Ticket>(KEY_TICKET)
        ticket?.let {
            binding.ticketModel = it
            viewModel.commentsList(it.id)
            setupObservers(it)
        }

        et_comment.afterTextChanged {
            viewModel.commentFormDataChange(et_comment.text.toString())
        }

        btn_comment.setOnClickListener {
            ticket?.id?.let { id -> viewModel.comment(id, et_comment.text.toString()) }
            et_comment.text.clear()
        }

        configureRecycler()
    }

    private fun configureRecycler() {
        commentAdapter = CommentAdapter(emptyList())
        rv_ticket_comments.apply {
            adapter = commentAdapter
        }
    }

    private fun setupObservers(ticket: Ticket) {
        viewModel.commentForm.observe(viewLifecycleOwner) {
            btn_comment.isEnabled = it
        }
        viewModel.networkState.observe(viewLifecycleOwner) { networkState ->
            when (networkState) {
                NetworkState.LOADING -> binding.ticketDetailsLoader.visible(activity?.window)
                else -> {
                    binding.ticketDetailsLoader.gone(activity?.window)
                    networkState.msg?.let { updateUserView(it) }
                }
            }
        }
        viewModel.commentResult.observe(viewLifecycleOwner) {
            if (!it.success.isNullOrEmpty())
                viewModel.commentsList(ticket.id)
            updateUserView(it.success ?: it.error ?: "")
        }
        viewModel.commentListResult.observe(viewLifecycleOwner) {
            commentAdapter.submitList(it)
        }
    }

    private fun updateUserView(message: String) {
        if (message.isNotEmpty()) activity?.showToast(message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback.remove()
    }
}
