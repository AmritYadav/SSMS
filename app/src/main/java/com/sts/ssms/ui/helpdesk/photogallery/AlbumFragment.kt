package com.sts.ssms.ui.helpdesk.photogallery

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.databinding.FragmentAlbumBinding
import com.sts.ssms.ui.helpdesk.photogallery.adapter.PhotosAdapter
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel
import com.sts.ssms.ui.helpdesk.photogallery.viewmodel.AlbumViewModel
import com.sts.ssms.utils.gone
import com.sts.ssms.utils.showToast
import com.sts.ssms.utils.updateEmptyState
import com.sts.ssms.utils.visible
import kotlinx.android.synthetic.main.app_bar_help_desk.*
import kotlinx.android.synthetic.main.fragment_album.*
import kotlinx.android.synthetic.main.layout_empty_state.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumFragment : Fragment() {

    private val albumViewModel by viewModel<AlbumViewModel>()

    private lateinit var binding: FragmentAlbumBinding

    private lateinit var fm: FragmentManager

    private var albumUiModel: AlbumUiModel? = null
    private lateinit var photosAdapter: PhotosAdapter

    companion object {
        const val KEY_ALBUM_UI_MODEL = "ALBUM_UI_MODEL"
        const val IS_FROM_PHOTO_GALLERY = "FROM_PHOTO_GALLERY"
        fun newInstance(bundle: Bundle): AlbumFragment {
            return AlbumFragment().apply {
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
        fm = childFragmentManager
        activity?.onBackPressedDispatcher?.addCallback(backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumUiModel = arguments?.getParcelable(KEY_ALBUM_UI_MODEL)
        val isFromPhotoGallery = arguments?.getBoolean(IS_FROM_PHOTO_GALLERY, false)
        albumUiModel?.let {
            binding.albumUiModel = it
            albumViewModel.loadAlbum(it.id)
            if (isFromPhotoGallery!!) {
                requireActivity().toolbar.title = it.albumName
            }
        }
        setupObserver()
        setupRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_notification)?.let {
            it.isVisible = false
        }
    }

    private fun setupObserver() {
        albumViewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> progress_album.visible(activity?.window)
                else -> {
                    progress_album.gone(activity?.window)
                    it.msg?.let { msg -> updateUserView(msg) }
                }
            }
        }
        albumViewModel.albumResult.observe(viewLifecycleOwner) {
            if (it.photos.isNotEmpty())
                photosAdapter.updatePhotos(it.photos)
            else
                view_empty_state.updateEmptyState(it.photos.isEmpty(), getString(R.string.prompt_empty_photos))

        }
    }
    private fun setupRecycler() {
        photosAdapter = PhotosAdapter(emptyList()) { position ->
            ImagePagerFragment.newInstance(photosAdapter.getImageModels(), position).apply {
                val transaction = fm.beginTransaction()
                show(transaction, ImagePagerFragment::javaClass.name)
            }
        }
        binding.rvPhotos.apply {
            adapter = photosAdapter
        }
    }

    private fun updateUserView(msg: String) = activity?.showToast(msg)

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback.remove()
    }
}
