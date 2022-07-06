package com.sts.ssms.ui.helpdesk.notification

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.notification.adapter.NotificationAdapter
import com.sts.ssms.ui.helpdesk.notification.model.SsmsNotification.NotificationDetail
import kotlinx.android.synthetic.main.fragment_notification.*

const val KEY_NOTIFICATIONS = "NOTIFICATIONS"
const val KEY_ACTIVE_MENUS = "ACTIVE_MENUS"

class NotificationFragment : Fragment() {

    private var activeMenus: List<Int>? = null

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
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notifications = arguments?.getParcelableArrayList<NotificationDetail>(KEY_NOTIFICATIONS)
        activeMenus = arguments?.getIntegerArrayList(KEY_ACTIVE_MENUS)
        rv_notifications.apply {
            notifications?.let {
                adapter = NotificationAdapter(it) { navItem -> checkFragmentVisibility(navItem) }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_notification).apply {
            isVisible = false
        }
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        return AnimatorInflater.loadAnimator(
            activity,
            if (enter) R.animator.slide_up else R.animator.slide_down
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback.remove()
    }

    private fun checkFragmentVisibility(navItem: Int) {
        activeMenus?.let {
            if (it.contains(navItem)) {
                view?.let { v -> Navigation.findNavController(v).navigate(navItem) }
            }
        }
    }
}
