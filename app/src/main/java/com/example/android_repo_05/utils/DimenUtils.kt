package com.example.android_repo_05.utils

import android.content.Context
import kotlin.math.roundToInt

object DimenUtils {
    fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }
}