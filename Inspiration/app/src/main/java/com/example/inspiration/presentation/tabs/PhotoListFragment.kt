package com.example.inspiration.presentation.tabs

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.widget.ListPopupWindow
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

        binding.motionContainerPhoto.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                Log.d("qqq", "$startId")

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
        //filterPhoto()
    }

    private fun initPhotoList(){
        photoAdapter = PhotoAdapter {

        }
        with(binding.photoItemList){
            adapter = photoAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(ItemPhotoOffset(requireContext()))
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel(){
        viewModel.getPhotos().onEach(::setPhotosAdapter).launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private suspend fun setPhotosAdapter(photos: PagingData<Photo>){
        photoAdapter?.submitData(photos)
    }

    private fun searchExpandedListener(){
        binding.photoToolbar.menu.findItem(R.id.actionSearch)
            .expandedSearch(
                isExpand = {searchTextListener()},
                isCollapse = {setSearchText("")}
            )
    }

    private fun searchTextListener(){
        if (job == null){
            job = (binding.photoToolbar.menu.findItem(R.id.actionSearch).actionView as SearchView)
                    .changeText().onEach(::setSearchText).launchIn(viewLifecycleOwner.lifecycleScope)
        }

    }

    private fun setSearchText(text: String){
        viewModel.setSeachText(text)
    }

    private fun filterPhoto(){
        binding.filterFab.setOnClickListener { view: View ->

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        photoAdapter = null
        job = null
    }
}