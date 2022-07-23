package com.example.android_repo_05.utils.enums

enum class TimeUnit(val timeUnit: Int) {
    Second(60),
    Minute(60 * Second.timeUnit),
    Hour(24 * Minute.timeUnit),
    Day(30 * Hour.timeUnit),
    MONTH(12 * Day.timeUnit)
}
