package com.example.inspiration.presentation.onboarding

import android.os.Bundle
import android.view.View


import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.inspiration.R
import com.example.inspiration.adapters.ViewPagerAdapter
import com.example.inspiration.databinding.FragmentOnboardingBinding
import com.example.inspiration.utils.DepthPageTransformer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment: Fragment(R.layout.fragment_onboarding) {

    private val binding: FragmentOnboardingBinding by viewBinding(FragmentOnboardingBinding::bind)

    private var viewPageAdapter: ViewPagerAdapter? = null

    private val viewModel: OnboardingViewModel by viewModels()

    private val depthTransformation = DepthPageTransformer()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()

        positionTracking()

        binding.fabLeft.setOnClickListener { moveToLeftScreen() }

        binding.fabRight.setOnClickListener { moveToRightScreen() }

        binding.fabClose.setOnClickListener {

            viewModel.saveUserVerification()

            findNavController().navigate(R.id.action_onboardingFragment_to_authFragment)

        }

    }

    private fun initViewPager(){

        viewPageAdapter = ViewPagerAdapter(onboardingScreenInfoList = viewModel.getOnboardingScreenInfoList())

        binding.viewPager.adapter = viewPageAdapter

        binding.viewPager.setPageTransformer( depthTransformation )

        binding.springDotsIndicator.attachTo(binding.viewPager)
    }

    private fun positionTracking(){
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                visibleFab(position)
            }
        })

    }

    private fun visibleFab(position: Int){

        when(position){
            0 -> {
                binding.fabLeft.hide()
                binding.fabRight.show()
            }
            viewModel.getLastItem() -> {
                binding.fabRight.hide()
                binding.fabClose.show()
            }
            else -> {
                binding.fabRight.show()
                binding.fabClose.hide()
                binding.fabLeft.show()
            }
        }

    }

    private fun moveToLeftScreen(){
        if (binding.viewPager.currentItem != 0) {
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1)
        }
    }

    private fun moveToRightScreen(){
        if (binding.viewPager.currentItem != viewModel.getLastItem()) {
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1)
        }
    }

}