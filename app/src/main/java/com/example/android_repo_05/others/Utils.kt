package com.example.android_repo_05.others

import android.content.Context
import android.net.Uri
import com.example.android_repo_05.BuildConfig
import com.example.android_repo_05.others.enums.TimeUnit
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object Utils {
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.KOREA)

    fun getGithubIdentityRequestUri(): Uri = Uri.Builder().scheme("https").authority("github.com")
        .appendPath("login")
        .appendPath("oauth")
        .appendPath("authorize")
        .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
        .appendQueryParameter("scope", "repo,user,notifications")
        .build()

    fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

    fun calculateElapsedTime(updatedAt: String): String {
        try {
            simpleDateFormat.parse(updatedAt)?.let { data ->
                val curTime = Date().time
                val updateTime = data.time
                var diffTime = (curTime - updateTime) / 1000
                when {
                    diffTime < TimeUnit.Second.timeUnit -> {
                        return "${diffTime}초전"
                    }
                    diffTime < TimeUnit.Minute.timeUnit -> {
                        diffTime /= TimeUnit.Second.timeUnit
                        return "${diffTime}분전"
                    }
                    diffTime < TimeUnit.Hour.timeUnit -> {
                        diffTime /= TimeUnit.Minute.timeUnit
                        return "${diffTime}시간전"
                    }
                    diffTime < TimeUnit.Day.timeUnit -> {
                        diffTime /= TimeUnit.Hour.timeUnit
                        return "${diffTime}일전"
                    }
                    diffTime < TimeUnit.MONTH.timeUnit -> {
                        diffTime /= TimeUnit.Day.timeUnit
                        return "${diffTime}개월전"
                    }
                    else -> {
                        diffTime /= TimeUnit.MONTH.timeUnit
                        return "${diffTime}년전"
                    }
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }
}
