package com.example.android_repo_05.data.network

import com.example.android_repo_05.others.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiInstance {
    private val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

    val retrofit: GithubApi = Retrofit.Builder()
        .baseUrl(Constants.githubApiBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(GithubApi::class.java)
}