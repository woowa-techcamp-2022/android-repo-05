package com.example.android_repo_05.utils

import android.graphics.Color

object ColorUtils {
    val languageColorMap: MutableMap<String, Int> = mutableMapOf(
        "Kotlin" to Color.rgb(169, 123, 255),
        "Java" to Color.rgb(176, 114, 25),
        "Python" to Color.rgb(53, 114, 165),
        "JavaScript" to Color.rgb(241, 224, 90),
        "C++" to Color.rgb(243, 75, 125)
    )
}
