package com.example.inspiration.presentation

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible


import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.inspiration.R
import com.example.inspiration.adapters.ViewPagerAdapter
import com.example.inspiration.data.models.OnbordingScreenInfo
import com.example.inspiration.databinding.FragmentOnboardingBinding


class OnboardingFragment: Fragment(R.layout.fragment_onboarding) {

    private val binding: FragmentOnboardingBinding by viewBinding(FragmentOnboardingBinding::bind)

    private var viewPageAdapter: ViewPagerAdapter? = null

    private var itemListSize = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()

        visibleFab()

        binding.fabLeft.setOnClickListener { moveToLeftScreen() }

        binding.fabRight.setOnClickListener { moveToRightScreen() }

    }

    private fun initViewPager(){

        val onbordingScreenInfoList = getOnboardingScreenInfoList()

        itemListSize = onbordingScreenInfoList.size

        viewPageAdapter = ViewPagerAdapter(onboardingScreenInfoList = onbordingScreenInfoList)

        binding.viewPager.adapter = viewPageAdapter

        binding.springDotsIndicator.attachTo(binding.viewPager)
    }

    private fun getOnboardingScreenInfoList(): List<OnbordingScreenInfo>{

        return listOf(
            OnbordingScreenInfo(
                backgroundDrawable = R.drawable.ic_collections_1x,
                textOnboarding = R.string.onboarding_text_first
            ),
            OnbordingScreenInfo(
                backgroundDrawable = R.drawable.ic_photos_1x,
                textOnboarding = R.string.onboarding_text_first
            ),
            OnbordingScreenInfo(
                backgroundDrawable = R.drawable.ic_profile_1x,
                textOnboarding = R.string.onboarding_text_first
            )
        )
    }

    private fun visibleFab(){
        binding.fabLeft.isVisible = binding.viewPager.currentItem != 0
        binding.fabRight.isVisible = binding.viewPager.currentItem != itemListSize-1
    }

    private fun moveToLeftScreen(){
        if (binding.viewPager.currentItem != 0) {
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1)
        }
    }

    private fun moveToRightScreen(){
        if (binding.viewPager.currentItem != itemListSize-1) {
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1)
        }
    }

}