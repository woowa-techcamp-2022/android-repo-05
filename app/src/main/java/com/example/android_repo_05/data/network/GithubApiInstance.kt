package com.example.android_repo_05.data.network

import android.net.Uri
import com.example.android_repo_05.BuildConfig

object GithubApiInstance {
    fun getGithubIdentityRequestUri(): Uri = Uri.Builder().scheme("https").authority("github.com")
        .appendPath("login")
        .appendPath("oauth")
        .appendPath("authorize")
        .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
        .appendQueryParameter("scope", "repo,user,notifications")
        .build()
}