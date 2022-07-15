package com.example.android_repo_05.retrofit

import com.example.android_repo_05.base.CustomApplication.Companion.accessTokenFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking(Dispatchers.IO) { accessTokenFlow.first() }
        val req = chain.request().newBuilder().addHeader("Accept", "application/vnd.github+json")
            .addHeader("Authorization", "token $accessToken").build()
        return chain.proceed(req)
    }
}