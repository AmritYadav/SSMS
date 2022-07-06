package com.sts.ssms.ui.helpdesk.photogallery

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.base.ui.SearchItemClickCallback
import com.sts.ssms.ui.helpdesk.HelpDeskActivity
import com.sts.ssms.ui.helpdesk.photogallery.AlbumFragment.Companion.IS_FROM_PHOTO_GALLERY
import com.sts.ssms.ui.helpdesk.photogallery.AlbumFragment.Companion.KEY_ALBUM_UI_MODEL
import com.sts.ssms.ui.helpdesk.photogallery.adapter.PhotoGalleryAdapter
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel
import com.sts.ssms.ui.helpdesk.photogallery.viewmodel.PhotoGalleryViewModel
import com.sts.ssms.ui.search.KEY_NAV_TYPE
import com.sts.ssms.ui.search.SearchActivity
import com.sts.ssms.utils.NavType
import com.sts.ssms.utils.updateEmptyState
import kotlinx.android.synthetic.main.app_bar_help_desk.*
import kotlinx.android.synthetic.main.fragment_photo_gallery.*
import kotlinx.android.synthetic.main.layout_empty_state.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoGalleryFragment : Fragment(), SearchItemClickCallback<AlbumUiModel> {

    private val photoGalleryViewModel by viewModel<PhotoGalleryViewModel>()

    private lateinit var photoGalleryAdapter: PhotoGalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_photo_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecycler()
        setupObservables()
        configureSwipeRefresh()
    }

    private fun setupObservables() {
        photoGalleryViewModel.networkState?.observe(viewLifecycleOwner) { it ->
            photoGalleryAdapter.updateNetworkState(it)
            photoGalleryViewModel.albumList.value?.let {
                view_empty_state.updateEmptyState(
                    it.isEmpty(),
                    getString(R.string.prompt_empty_albums)
                )
            }
        }
        photoGalleryViewModel.albumList.observe(viewLifecycleOwner) {
            photoGalleryAdapter.submitList(it)
        }
    }

    private fun configureRecycler() {
        photoGalleryAdapter =
            PhotoGalleryAdapter(this) { photoGalleryViewModel.retry() }
        rv_albums.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = photoGalleryAdapter
        }
    }

    private fun navAlbumFragment(album: AlbumUiModel) {
        requireActivity().toolbar.title = album.albumName
        val bundle = Bundle().apply {
            putParcelable(KEY_ALBUM_UI_MODEL, album)
            putBoolean(IS_FROM_PHOTO_GALLERY, true)
        }
        view?.let { Navigation.findNavController(it).navigate(R.id.nav_album, bundle) }
    }


    private fun configureSwipeRefresh() {
        var isSwipeEnabled = false
        swipe_refresh_photo_gallery.apply {

            // Observing the NetworkState, to load fresh data or show retry button
            photoGalleryViewModel.refreshState?.observe(viewLifecycleOwner) {
                if (isSwipeEnabled) {
                    isRefreshing = it == NetworkState.LOADING
                    isSwipeEnabled = isRefreshing
                }
            }

            setOnRefreshListener {
                isSwipeEnabled = true
                photoGalleryViewModel.refreshAllData()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val searchMenuView: View =
                    (activity as HelpDeskActivity).toolbar.findViewById(R.id.action_search)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    searchMenuView, getString(R.string.transition_search_back)
                ).toBundle()

                val intent = Intent(activity, SearchActivity::class.java).apply {
                    action = Intent.ACTION_SEARCH
                    putExtras(Bundle().apply {
                        putParcelable(KEY_NAV_TYPE, NavType.PHOTO_GALLERY)
                    })
                }
                startActivity(intent, options)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(item: AlbumUiModel) {
        navAlbumFragment(item)
    }
}
