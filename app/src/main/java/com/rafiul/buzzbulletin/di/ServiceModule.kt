package com.rafiul.buzzbulletin.di

import com.rafiul.buzzbulletin.remote.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {
    @Singleton
    @Provides
    fun providesNewsApi(retrofit: Retrofit.Builder, client: OkHttpClient): NewsApi {
        return retrofit.client(client).build().create(NewsApi::class.java)
    }
}