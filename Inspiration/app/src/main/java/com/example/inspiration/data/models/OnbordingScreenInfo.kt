package com.example.inspiration.data.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnbordingScreenInfo(
    @DrawableRes val backgroundDrawable: Int,
    @StringRes val textOnboarding: Int
    )
