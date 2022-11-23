package com.example.inspiration.models

import com.example.domain.enum.Popular

data class FilterPhoto(
    val popular: String = Popular.LATEST.value
)
