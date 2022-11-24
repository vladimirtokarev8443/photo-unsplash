package com.example.inspiration.presentation.tabs.photo

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.domain.enum.Popular
import com.example.domain.models.Photo
import com.example.inspiration.R
import com.example.inspiration.adapters.PhotoAdapter
import com.example.inspiration.databinding.FragmentPhotoListBinding
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
    }

    private fun initPhotoList(){
        photoAdapter = PhotoAdapter { photoId ->
            val action = PhotoListFragmentDirections.actionPhotoListFragmentToDetailsPhoto(photoId)
            findNavController().navigate(action)
        }
        with(binding.photoItemList){
            adapter = photoAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(ItemPhotoOffset(requireContext()))
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel(){
        viewModel.getPhotos2().onEach(::setPhotosAdapter).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun setPhotosAdapter(photos: PagingData<Photo>){
        photoAdapter?.submitData(photos)
    }

    private fun searchExpandedListener(){
        binding.photoToolbar.menu.findItem(R.id.actionSearch)
            .expandedSearch(
                isExpand = {
                    searchTextListener()
                    binding.filterFab.hide()
                    binding.filter.motionPhotoFilter.transitionToStart()
                           },
                isCollapse = {setSearchText(""); binding.filterFab.show()}
            )
    }

    private fun searchTextListener(){
        if (job == null){
            job = (binding.photoToolbar.menu.findItem(R.id.actionSearch).actionView as SearchView)
                    .changeText().onEach(::setSearchText).launchIn(viewLifecycleOwner.lifecycleScope)
        }

    }

    private fun setSearchText(text: String){
        //viewModel.setSeachText(text)
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

    private fun filterPhotoWithSearch(){

    }


    override fun onDestroyView() {
        super.onDestroyView()
        photoAdapter = null
        job = null
    }
}