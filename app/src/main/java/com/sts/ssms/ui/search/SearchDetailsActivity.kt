package com.sts.ssms.ui.search

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.dashboard.DashboardDetailFragment
import com.sts.ssms.ui.helpdesk.dashboard.model.Ticket
import com.sts.ssms.ui.helpdesk.noticeboard.NoticeDetailsFragment
import com.sts.ssms.ui.helpdesk.officialcommunication.CommDetailFragment
import com.sts.ssms.ui.helpdesk.officialcommunication.models.Communication
import com.sts.ssms.ui.helpdesk.photogallery.AlbumFragment
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel
import com.sts.ssms.ui.helpdesk.staff.StaffDetailsFragment
import com.sts.ssms.ui.helpdesk.vendor.VendorDetailsFragment
import com.sts.ssms.ui.search.model.SearchItem
import com.sts.ssms.ui.search.officalcommunication.KEY_COMMUNICATION_SEARCH_ITEM
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.activity_search_details.*
import java.lang.IllegalStateException

const val KEY_SEARCH_ITEM = "SEARCH_ITEM"
const val KEY_TICKET_SEARCH_ITEM = "TICKET_SEARCH_ITEM"

class SearchDetailsActivity : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_details)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val uiModel = intent.getParcelableExtra<SearchItem>(KEY_SEARCH_ITEM)
        val navType = intent.getParcelableExtra<NavType>(KEY_NAV_TYPE)

        setupActionBar(details_toolbar) {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        navType ?: return

        val fragment = when (navType) {
            NavType.VENDOR -> {
                details_toolbar.title = getString(R.string.title_vendor_details)
                VendorDetailsFragment.newInstance(Bundle().apply {
                    putParcelable(VendorDetailsFragment.KEY_VENDOR_UI_MODEL, uiModel)
                })
            }
            NavType.STAFF -> {
                details_toolbar.title = getString(R.string.title_staff_details)
                StaffDetailsFragment.newInstance(Bundle().apply {
                    putParcelable(StaffDetailsFragment.KEY_STAFF_UI_MODEL, uiModel)
                })
            }
            NavType.TICKET -> {
                details_toolbar.title = getString(R.string.title_ticket_details)
                val ticket = intent.getParcelableExtra<Ticket>(KEY_TICKET_SEARCH_ITEM)
                details_toolbar.title = getString(R.string.title_ticket_details)
                DashboardDetailFragment.newInstance(ticket!!)
            }
            NavType.PHOTO_GALLERY -> {
                details_toolbar.title = (uiModel as AlbumUiModel).albumName
                AlbumFragment.newInstance(Bundle().apply {
                    putParcelable(AlbumFragment.KEY_ALBUM_UI_MODEL, uiModel)
                })
            }
            NavType.NOTICE_BOARD -> {
                details_toolbar.title = getString(R.string.title_notice_details)
                NoticeDetailsFragment.newInstance(Bundle().apply {
                    putParcelable(NoticeDetailsFragment.KEY_NOTICE_MODEL, uiModel)
                })
            }
            NavType.OFFICIAL_COMMUNICATION -> {
                details_toolbar.title = getString(R.string.title_official_comm_details)
                val communication = intent.getParcelableExtra<Communication>(KEY_COMMUNICATION_SEARCH_ITEM)
                CommDetailFragment.newInstance(Bundle().apply {
                    putParcelable(CommDetailFragment.KEY_COMM_MODEL, communication)
                })
            }
            else -> throw IllegalStateException("Invalid Nav Type")
        }
        addFragmentToActivity(fragment, R.id.fragment_container)
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
                if (!rationalePermission) {
                    sendUserToAppSettingsForPermissionAccess()
                }
            } else {
                when (val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)) {
                    is StaffDetailsFragment -> fragment.onPermissionGranted()
                }
            }
        }
    }
}
