package com.rafiul.buzzbulletin.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rafiul.buzzbulletin.remote.NewsApi
import com.rafiul.buzzbulletin.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    companion object {
        private const val CONNECT_TIMEOUT_SECONDS = 20L
        private const val WRITE_TIMEOUT_SECONDS = 30L
        private const val READ_TIMEOUT_SECONDS = 20L
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()


    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    // Provides the ApiKeyProvider dependency
    @Provides
    @Singleton
    fun provideApiKeyProvider(): ApiKeyProvider = DefaultApiKeyProvider()

    // Provides the AuthInterceptor dependency with the injected ApiKeyProvider
    @Provides
    @Singleton
    fun provideAuthInterceptor(apiKeyProvider: ApiKeyProvider): AuthInterceptor =
        AuthInterceptor(apiKeyProvider)


    @Singleton
    @Provides
    fun getRetrofitBuilder(gsonConverterFactory: GsonConverterFactory): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS).build()
    }

}