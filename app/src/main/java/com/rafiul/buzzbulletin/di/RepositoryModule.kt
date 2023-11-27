package com.rafiul.buzzbulletin.di


import com.rafiul.buzzbulletin.remote.NewsApi
import com.rafiul.buzzbulletin.remote.NewsRepositoryImpl
import com.rafiul.buzzbulletin.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(api: NewsApi): NewsRepository = NewsRepositoryImpl(api)
}