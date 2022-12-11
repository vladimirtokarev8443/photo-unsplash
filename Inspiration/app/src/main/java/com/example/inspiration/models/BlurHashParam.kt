package com.example.inspiration.models

data class BlurHashParam(
    val blurHash: String?,
    private val widthFull: Int,
    private val heightFull: Int
){
    val width get() = widthFull/100//if (widthFull < 1000) 1 else widthFull / 1000
    val height get() = heightFull/100//if (heightFull < 1000) 1 else heightFull / 1000
}

