package com.sts.ssms.ui.helpdesk

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.sts.ssms.R
import com.sts.ssms.data.login.repository.local.LoginLocalDataSourceImpl.Companion.KEY_USER_AVATAR
import com.sts.ssms.data.login.repository.local.LoginLocalDataSourceImpl.Companion.KEY_USER_NAME
import com.sts.ssms.data.society.repository.SocietyLocalDataSource.Companion.KEY_SELECTED_SOCIETY_ID
import com.sts.ssms.ui.helpdesk.documents.DocumentsFragment
import com.sts.ssms.ui.helpdesk.notification.KEY_ACTIVE_MENUS
import com.sts.ssms.ui.helpdesk.notification.KEY_NOTIFICATIONS
import com.sts.ssms.ui.helpdesk.notification.model.SsmsNotification.NotificationDetail
import com.sts.ssms.ui.helpdesk.staff.StaffDetailsFragment
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.activity_help_desk.*
import kotlinx.android.synthetic.main.nav_header_help_desk.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.schedule

class HelpDeskActivity : AppCompatActivity() {

    private val viewModel by viewModel<HelpDeskViewModel>()
    private val prefs by inject<SharedPreferences>()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController

    private var txtNotificationItemCount: TextView? = null
    private var mNotificationItemCount = 0
    private lateinit var notifications: List<NotificationDetail>

    private lateinit var timerTask: TimerTask
    private val timer = Timer()

    private val preferenceListener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            when (key) {
                KEY_USER_AVATAR -> iv_nav_avatar.loadCircularImage(prefs.getString(key, ""))
                KEY_USER_NAME -> tv_nav_header_user.text = prefs.getString(key, "")
                KEY_SELECTED_SOCIETY_ID -> resetMenuOnSocietyChange()
            }
        }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_desk)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_profile,
                R.id.nav_dashboard,
                R.id.nav_conversations,
                R.id.nav_my_flat,
                R.id.nav_amenity,
                R.id.nav_documents,
                R.id.nav_gallery,
                R.id.nav_society_events,
                R.id.nav_notice_board,
                R.id.nav_official_communication,
                R.id.nav_payment_details,
                R.id.nav_vendor_management,
                R.id.nav_staff_management,
                R.id.nav_features,
                R.id.nav_bye_laws
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, _, _ ->
            drawerLayout.closeDrawers()
        }

        setupMenu()

        btn_sing_out.setOnClickListener {
            drawerLayout.closeDrawers()
            showLogoutAlert()
        }

        viewModel.loggedInUser?.observe(this) {
            tv_nav_header_user.text = it.displayName
            iv_nav_avatar.loadCircularImage(it.avatar)
        }

        viewModel.isLoggedOut.observe(this@HelpDeskActivity) { loggedOut ->
            if (loggedOut) {
                finish()
                navigationTo(NAVIGATE_TO_LOGIN)
            }
        }

        viewModel.notificationResult.observe(this@HelpDeskActivity) {
            mNotificationItemCount = it.total
            notifications = it.notificationDetails
            setupBadge()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_helpdesk, menu)
        menu?.findItem(R.id.action_notification)?.let { menuItem ->
            val actionView = menuItem.actionView
            txtNotificationItemCount = actionView.findViewById(R.id.cart_badge)
            setupBadge()
            actionView.setOnClickListener {
                onOptionsItemSelected(menuItem)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_notification) {
            if (!notifications.isNullOrEmpty()) {
                val bundle = Bundle().apply {
                    putParcelableArrayList(KEY_NOTIFICATIONS, notifications as ArrayList)
                    putIntegerArrayList(KEY_ACTIVE_MENUS, viewModel.menuList as ArrayList)
                }
                navController.navigate(R.id.nav_notification, bundle)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        prefs.registerOnSharedPreferenceChangeListener(preferenceListener)
        timerTask = timer.schedule(200, 60 * 1000) {
            viewModel.checkNotifications()
        }
    }

    override fun onPause() {
        super.onPause()
        prefs.unregisterOnSharedPreferenceChangeListener(preferenceListener)
        timerTask.cancel()
    }

    private fun showLogoutAlert() {
        AlertDialog.Builder(this@HelpDeskActivity)
            .setMessage("Are you sure, you want to logout?")
            .setCancelable(false)
            .setPositiveButton("Logout") { _, _ -> viewModel.logout() }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    /**
     * gets menu active menu of the selected society
     * and sets the default destination depending on the
     * menu list size
     */
    private fun setupMenu() {
        val menus = viewModel.menuList
        navView.menu.forEach {
            it.isVisible = menus.contains(it.itemId) && it.itemId != R.id.nav_features
        }

        val ssmsGraph = navController.graph
        ssmsGraph.startDestination = if (menus.size > 3) menus[3] else menus[0]
        navController.graph = ssmsGraph
    }

    /**
     * Update the menu and check for Notifications on society change
     */
    private fun resetMenuOnSocietyChange() {
        val menus = viewModel.menuList
        navView.menu.forEach {
            it.isVisible = menus.contains(it.itemId) && it.itemId != R.id.nav_features
        }
        viewModel.checkNotifications()
    }

    private fun setupBadge() {
        txtNotificationItemCount?.let {
            it.text = mNotificationItemCount.coerceAtMost(99).toString()
            it.visibleGone(mNotificationItemCount > 0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            val permission = permissions[0]
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                val rationalePermission = shouldShowRequestPermissionRationale(permission)
                if (!rationalePermission) sendUserToAppSettingsForPermissionAccess()
            } else {
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    ?.childFragmentManager?.primaryNavigationFragment?.let {
                    when (it) {
                        is StaffDetailsFragment -> it.onPermissionGranted()
                        is DocumentsFragment -> it.onPermissionGranted()
                    }
                }
            }
        }
    }
}