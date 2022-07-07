package com.example.inspiration.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inspiration.data.models.OnbordingScreenInfo
import com.example.inspiration.databinding.ItemOnboardingBinding

class ViewPagerAdapter(
    private val onboardingScreenInfoList: List<OnbordingScreenInfo>
): RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOnboardingBinding.inflate(inflater, parent, false)

        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(onboardingScreenInfo = onboardingScreenInfoList[position])
    }

    override fun getItemCount(): Int = onboardingScreenInfoList.size



    class PagerViewHolder(
       private val  binding: ItemOnboardingBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(onboardingScreenInfo: OnbordingScreenInfo){
            binding.itemContainer.setBackgroundResource(onboardingScreenInfo.backgroundDrawable)
            binding.onboardingTextView.setText(onboardingScreenInfo.textOnboarding)
        }
    }
}