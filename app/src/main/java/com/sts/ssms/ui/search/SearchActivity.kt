package com.sts.ssms.ui.search

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sts.ssms.R
import com.sts.ssms.base.ui.fragment.BaseSearchFragment
import com.sts.ssms.ui.search.amenity.SearchAllAmenityReqFragment
import com.sts.ssms.ui.search.documents.SearchDocumentsFragment
import com.sts.ssms.ui.search.model.SearchItem
import com.sts.ssms.ui.search.noticeboard.SearchNoticeFragment
import com.sts.ssms.ui.search.photogallery.SearchAlbumFragment
import com.sts.ssms.ui.search.staff.SearchStaffFragment
import com.sts.ssms.ui.search.vendor.SearchVendorFragment
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.activity_search.*

const val KEY_NAV_TYPE = "NAV_TYPE"
const val KEY_FILTER_ID = "FILTER_ID"

class SearchActivity : AppCompatActivity() {

    var navType: NavType? = null
    var filterId: Int = 0

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        search_view.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        // inputType & ime options seem to be ignored from XML! Set in code
        search_view.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        search_view.imeOptions = search_view.imeOptions or EditorInfo.IME_ACTION_SEARCH or
                EditorInfo.IME_FLAG_NO_EXTRACT_UI or EditorInfo.IME_FLAG_NO_FULLSCREEN
        search_view.requestFocus()

        search_back.setOnClickListener {
            finishAfterTransition()
        }

        navType = intent.getParcelableExtra(KEY_NAV_TYPE)
        filterId = intent.getIntExtra(KEY_FILTER_ID, 0)

        navType?.let {

            val searchMsgId = when (it) {
                NavType.VENDOR -> R.string.prompt_search_vendor
                NavType.STAFF -> R.string.prompt_search_staff
                NavType.PHOTO_GALLERY -> R.string.prompt_search_album
                NavType.NOTICE_BOARD -> R.string.prompt_search_notice
                NavType.DOCUMENTS -> R.string.prompt_search_document
                NavType.ALL_AMENITY_REQUEST -> R.string.prompt_search_all_amenity_request
                else -> R.string.prompt_search_default
            }

            search_view.queryHint = getString(searchMsgId)

            val fragment = when (navType) {
                NavType.VENDOR -> SearchVendorFragment()
                NavType.STAFF -> SearchStaffFragment()
                NavType.PHOTO_GALLERY -> SearchAlbumFragment()
                NavType.DOCUMENTS -> SearchDocumentsFragment()
                NavType.ALL_AMENITY_REQUEST -> SearchAllAmenityReqFragment()
                else -> SearchNoticeFragment()
            }

            addFragmentToActivity(fragment, R.id.fragment_search_container)
            configureSearch(fragment)
            fragment.setFilterId(filterId)
        }
    }

    private fun <T : SearchItem> configureSearch(fragment: BaseSearchFragment<T>) {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                fragment.search(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                val hasQuery = query.isNotEmpty()
                fragment_search_container.visibleGone(hasQuery)
                if (hasQuery) {
                    fragment.search(query)
                }
                return true
            }
        })
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
                when (val fragment =
                    supportFragmentManager.findFragmentById(R.id.fragment_search_container)) {
                    is SearchDocumentsFragment -> fragment.onPermissionGranted()
                }
            }
        }
    }
}
