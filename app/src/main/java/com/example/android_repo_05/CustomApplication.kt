package com.example.android_repo_05

import android.app.Application
import android.content.Context
import com.example.android_repo_05.utils.modules.GlideApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
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

    override fun onLowMemory() {
        super.onLowMemory()
        GlideApp.get(this).clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        GlideApp.get(this).trimMemory(level)
    }
}