package com.sts.ssms.ui.search

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.dashboard.model.Ticket
import com.sts.ssms.ui.search.ticket.KEY_FILTER_ACTION
import com.sts.ssms.ui.search.ticket.KEY_TICKETS
import com.sts.ssms.ui.search.ticket.SearchTicketFragment
import com.sts.ssms.utils.addFragmentToActivity
import com.sts.ssms.utils.visibleGone
import kotlinx.android.synthetic.main.activity_search.*

class SearchTicketActivity : AppCompatActivity() {

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

        val tickets = intent.getParcelableArrayListExtra<Ticket>(KEY_TICKETS) as ArrayList
        val filterAction = intent.getIntExtra(KEY_FILTER_ACTION, 0)

        val fragment = SearchTicketFragment.newInstance(tickets, filterAction)

        addFragmentToActivity(fragment, R.id.fragment_search_container)

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
}
