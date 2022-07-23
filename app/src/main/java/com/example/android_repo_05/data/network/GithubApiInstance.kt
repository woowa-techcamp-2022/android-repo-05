package com.example.android_repo_05.data.network

import android.net.Uri
import com.example.android_repo_05.BuildConfig
import com.example.android_repo_05.utils.Constants
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

    fun getGithubIdentityRequestUri(): Uri = Uri.Builder().scheme("https").authority("github.com")
        .appendPath("login")
        .appendPath("oauth")
        .appendPath("authorize")
        .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
        .appendQueryParameter("scope", "repo,user,notifications")
        .build()
}