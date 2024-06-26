package com.rafiul.buzzbulletin.di

import com.rafiul.buzzbulletin.utils.API_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthInterceptor @Inject constructor(
    private val apiKeyProvider: ApiKeyProvider
) : Interceptor {

    companion object{
        private const val AUTHORIZATION_KEY = "x-api-key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        requestBuilder.addHeader(AUTHORIZATION_KEY, apiKeyProvider.getApiKey())
        return chain.proceed(requestBuilder.build())
    }
}
interface ApiKeyProvider {
    fun getApiKey(): String
}

class DefaultApiKeyProvider @Inject constructor() : ApiKeyProvider {
    override fun getApiKey(): String  = API_KEY
}
