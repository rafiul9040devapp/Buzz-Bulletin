package com.rafiul.buzzbulletin.di

import com.rafiul.buzzbulletin.utils.API_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder().also {
            it.addHeader("x-api-key", API_KEY)
        }

        return chain.proceed(request.build())
    }
}