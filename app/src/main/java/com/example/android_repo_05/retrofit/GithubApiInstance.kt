package com.example.android_repo_05.retrofit

import com.example.android_repo_05.others.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiInstance {
    val retrofit: GithubApi = Retrofit.Builder()
        .baseUrl(Constants.githubApiBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubApi::class.java)
}