package com.example.inspiration.presentation.tabs.photo

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.domain.enum.Popular
import com.example.domain.models.Photo
import com.example.inspiration.R
import com.example.inspiration.adapters.PhotoAdapter
import com.example.inspiration.databinding.FragmentPhotoListBinding
import com.example.inspiration.presentation.tabs.photo.DetailsPhotoFragment.Companion.LIKE_KEY
import com.example.inspiration.presentation.tabs.photo.DetailsPhotoFragment.Companion.PHOTO_ID_KEY
import com.example.inspiration.presentation.tabs.photo.DetailsPhotoFragment.Companion.REQUEST_CODE
import com.example.inspiration.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PhotoListFragment: BaseFragment<FragmentPhotoListBinding>(FragmentPhotoListBinding::inflate) {

    private val viewModel: PhotoListViewModel by viewModels()
    private var photoAdapter: PhotoAdapter? = null
    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPhotoList()

        observeViewModel()

        searchExpandedListener()

        filterPhoto()

        getResultToFragment()
    }

    private fun initPhotoList(){
        photoAdapter = PhotoAdapter { photoId, isClick ->
            isClick?.let {
                viewModel.updateLike(it, photoId)
            } ?: navigateToDetailsPhoto(photoId)

        }
        with(binding.photoItemList){
            adapter = photoAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(ItemPhotoOffset(requireContext()))
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel(){
        viewModel.pagingDataFlow.onEach(::setPhotosAdapter).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun setPhotosAdapter(photos: PagingData<Photo>){
        photoAdapter?.submitData(photos)
    }

    private fun searchExpandedListener(){
        binding.photoToolbar.menu.findItem(R.id.actionSearch)
            .expandedSearch(
                isExpand = { expandSearchView() },
                isCollapse = { collapseSearchView() }
            )
    }

    private fun expandSearchView(){
        searchTextListener()
        binding.filter.motionPhotoFilter.transitionToStart()
        binding.motionContainerPhoto.setTransition(R.id.fabTransition)
        binding.motionContainerPhoto.transitionToEnd()
    }

    private fun searchTextListener(){
        if (job == null){
            job = (binding.photoToolbar.menu.findItem(R.id.actionSearch).actionView as SearchView)
                .changeText()
                .onEach(::setSearchText)
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

    }

    private fun collapseSearchView(){
        job = null
        setSearchText("")
        binding.motionContainerPhoto.transitionToStart()
        binding.motionContainerPhoto.setTransition(R.id.toolbarTransition)
    }

    private fun setSearchText(text: String){
        viewModel.setFilterQuerySearch(query = text)
    }

    private fun filterPhoto(){
        binding.filterFab.setOnClickListener {
            binding.filter.motionPhotoFilter.transitionToEnd()
            binding.filter.motionPhotoFilter.transitionToStart()
        }

        binding.filter.chipGroup.setOnCheckedStateChangeListener { group,_ ->
            when(group.checkedChipId){
                R.id.latestChip -> viewModel.setfilterPopular(Popular.LATEST.value)
                R.id.oldestChip -> viewModel.setfilterPopular(Popular.OLDEST.value)
                else -> viewModel.setfilterPopular(Popular.POPULAR.value)
            }
            binding.filter.motionPhotoFilter.transitionToStart()
        }
    }

    private fun getResultToFragment(){
        parentFragmentManager.setFragmentResultListener(REQUEST_CODE, viewLifecycleOwner){requestCode, data ->
            val isLike = data.getBoolean(LIKE_KEY)
            val photoId = data.getString(PHOTO_ID_KEY)
            photoId?.let{ viewModel.setLocalChange(it, isLike) }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        photoAdapter = null
        job = null
    }
}