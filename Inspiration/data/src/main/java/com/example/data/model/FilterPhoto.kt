package com.example.data.model

import com.example.domain.enum.Popular
import com.example.domain.enum.PopularSearch

data class FilterPhoto(
    val querySearch: String = "",
    val popular: String = Popular.LATEST.value,
    val popularSearch: String = PopularSearch.RELEVANT.value,
    val orientation: String = ""
)
