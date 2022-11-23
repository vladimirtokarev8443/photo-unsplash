package com.example.inspiration.models

import com.example.domain.enum.Orientation
import com.example.domain.enum.PopularSearch

data class FilterSearchPhoto(
    val query: String = "",
    val popular: String = PopularSearch.RELEVANT.value,
    //val orientation: Enum<Orientation>? = null
)
