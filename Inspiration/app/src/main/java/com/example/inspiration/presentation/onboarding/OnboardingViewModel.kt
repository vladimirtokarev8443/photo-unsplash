package com.example.inspiration.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inspiration.R
import com.example.inspiration.data.enum.Verification
import com.example.inspiration.data.models.OnbordingScreenInfo
import com.example.inspiration.data.models.UserVerification
import com.example.inspiration.data.repository.UserVerificationRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userVerificationRepositoryImpl: UserVerificationRepositoryImpl
): ViewModel() {

    private val onboardingScreenInfoList = listOf(
        OnbordingScreenInfo(
            backgroundDrawable = R.drawable.onboarding_screen,
            textOnboarding = R.string.onboarding_text_first
        ),
        OnbordingScreenInfo(
            backgroundDrawable = R.drawable.onboarding_screen,
            textOnboarding = R.string.onboarding_text_second
        ),
        OnbordingScreenInfo(
            backgroundDrawable = R.drawable.onboarding_screen,
            textOnboarding = R.string.onboarding_text_third
        )
    )

    fun getOnboardingScreenInfoList() = onboardingScreenInfoList

    fun getLastItem() = onboardingScreenInfoList.size - 1

    fun saveUserVerification(){
        viewModelScope.launch {
            userVerificationRepositoryImpl.saveUserVerification(
                UserVerification(Verification.NOT_VERIFIED)
            )
        }
    }
}