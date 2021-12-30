package com.codelabs.pagedusers.common.di

import com.codelabs.pagedusers.common.api.RandomUserMeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Provides
    @Singleton
    fun provideRandomUserMeApi(okHttpClient: OkHttpClient): RandomUserMeApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://randomuser.me/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomUserMeApi::class.java)
    }
}