package com.example.android_repo_05.base

import android.app.Application
import android.content.Context

class CustomApplication : Application() {
    companion object {
        lateinit var instance: CustomApplication
            private set
    }

    val context: Context get() = applicationContext

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}