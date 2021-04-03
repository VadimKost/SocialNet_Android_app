package com.v.vsocial.utils

import com.v.vsocial.api.Auth
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(var Auth: Auth): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "${Auth.getUserCredentials()}")
            .build()
        return chain.proceed(newRequest)
    }
}