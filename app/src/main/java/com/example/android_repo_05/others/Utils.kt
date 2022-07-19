package com.example.android_repo_05.others

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.TypedValue
import com.example.android_repo_05.BuildConfig
import com.example.android_repo_05.others.enums.TimeUnit
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.KOREA)

    val languageColorMap : MutableMap<String, Int> = mutableMapOf(
        "Kotlin" to Color.rgb(169,123,255),
        "Java" to Color.rgb(176,114,25),
        "Python" to Color.rgb(53,114,165),
        "JavaScript" to Color.rgb(241,224,90),
        "C++" to Color.rgb(243,75,125)
    )

    fun getGithubIdentityRequestUri(): Uri = Uri.Builder().scheme("https").authority("github.com")
        .appendPath("login")
        .appendPath("oauth")
        .appendPath("authorize")
        .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
        .appendQueryParameter("scope", "repo,user,notifications")
        .build()

    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
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
