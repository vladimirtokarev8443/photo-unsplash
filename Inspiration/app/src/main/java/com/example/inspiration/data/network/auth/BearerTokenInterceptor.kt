package com.example.inspiration.data.network.auth

import okhttp3.Interceptor
import okhttp3.Response

class BearerTokenInterceptor: Interceptor {

    //private val accessToken = "hT1aaYtIncUyf2qxEf5OY3hVQsBjUvbePewXhd_E7wA"

    override fun intercept(chain: Interceptor.Chain): Response {
        val modifiedRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${AccessToken.accessToken}")
            .build()

        val response = chain.proceed(modifiedRequest)

        return response
    }
}