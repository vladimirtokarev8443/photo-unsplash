package com.example.inspiration.di.data

import com.example.data.api.UnsplashApi
import com.example.data.repository.AccessToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkingModul {

    @BearerTokenInterceptor
    @Provides
    @Singleton
    fun providesBearerTokenInterceptor(): Interceptor {

       return Interceptor{ chain ->
           val modifiedRequest = chain.request().newBuilder()
               .addHeader("Authorization", "Bearer ${AccessToken.token}")
               .build()

           val response = chain.proceed(modifiedRequest)

           response
       }

    }

    @LoggingInterceptor
    @Provides
    @Singleton
    fun providesLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provodesOkHttpClient(
        @BearerTokenInterceptor bearerInterceptor: Interceptor,
        @LoggingInterceptor interseptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(bearerInterceptor)
            .addNetworkInterceptor(interseptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesApi(retrofit: Retrofit): UnsplashApi {
        return retrofit.create()
    }
}

@Qualifier
annotation class BearerTokenInterceptor

@Qualifier
annotation class LoggingInterceptor