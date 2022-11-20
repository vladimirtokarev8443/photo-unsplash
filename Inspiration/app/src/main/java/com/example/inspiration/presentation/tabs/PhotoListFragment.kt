package com.example.inspiration.presentation.tabs

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.inspiration.R
import com.example.inspiration.adapters.PhotoAdapter
import com.example.inspiration.databinding.FragmentPhotoListBinding
import com.example.inspiration.utils.BaseFragment
import com.example.inspiration.utils.ItemPhotoOffset
import com.example.inspiration.utils.setIconSearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@AndroidEntryPoint
class PhotoListFragment: BaseFragment<FragmentPhotoListBinding>(FragmentPhotoListBinding::inflate) {

    private val viewModel: PhotoListViewModel by viewModels()
    private var photoAdapter: PhotoAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().setIconSearchView()
        initPhotoList()
        //observeViewModel()

        val search = binding.photoToolbar.menu.findItem(R.id.actionSearch)
        (search.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchPhoto(query)
                        .onStart {
                            photoAdapter?.submitData(PagingData.empty())
                        }
                        .onEach {
                            photoAdapter?.submitData(it)
                        }
                    .launchIn(viewLifecycleOwner.lifecycleScope) }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })




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
        viewModel.photoList
            .onEach {
                photoAdapter?.submitData(it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        photoAdapter = null
    }
}