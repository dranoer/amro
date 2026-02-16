package com.amro.app.data.remote.auth

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val bearerToken: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $bearerToken")
            .build()
        return chain.proceed(req)
    }
}