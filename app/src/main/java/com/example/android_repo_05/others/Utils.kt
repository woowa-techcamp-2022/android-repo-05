package com.example.android_repo_05.others

import android.content.Context
import android.net.Uri
import android.util.TypedValue
import com.example.android_repo_05.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

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

    val curTime = Date().time
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.KOREA)
}
enum class TimeUnit(val timeUnit : Int) {
    Second(60),
    Minute(60 * Second.timeUnit),
    Hour(24 * Minute.timeUnit),
    Day(30 * Hour.timeUnit),
    MONTH(12 * Day.timeUnit)
}

