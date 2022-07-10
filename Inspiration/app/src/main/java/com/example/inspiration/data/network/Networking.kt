package com.example.inspiration.data.network

import com.example.inspiration.data.network.auth.BearerTokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Networking {
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(BearerTokenInterceptor())
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    val unsplashApi: Api
        get() = retrofit.create()
}