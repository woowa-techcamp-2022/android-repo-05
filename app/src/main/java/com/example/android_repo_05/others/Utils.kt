package com.example.android_repo_05.others

import android.content.Context
import android.net.Uri
import android.util.TypedValue
import com.example.android_repo_05.BuildConfig

object Utils {
    fun getGithubIdentityRequestUri(): Uri = Uri.Builder().scheme("https").authority("github.com")
        .appendPath("login")
        .appendPath("oauth")
        .appendPath("authorize")
        .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
        .appendQueryParameter("scope", "repo,user,notifications")
        .build()

    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }
}

