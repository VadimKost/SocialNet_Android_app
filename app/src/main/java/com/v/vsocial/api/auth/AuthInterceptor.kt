package com.v.vsocial.api.auth

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(var Auth: Auth): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("Authorization", "${Auth.getUserCredentials()}")
            .build()
        val response=chain.proceed(request)
        if (response.code()==401){
            Auth.removeUserCredentials()
        }
        return response
    }
}