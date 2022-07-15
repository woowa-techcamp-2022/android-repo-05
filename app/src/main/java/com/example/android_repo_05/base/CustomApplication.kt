package com.example.android_repo_05.base

import android.app.Application
import com.example.android_repo_05.repositories.TokenRepository

class CustomApplication : Application() {
    companion object {
        lateinit var tokenRepository: TokenRepository
    }

    override fun onCreate() {
        super.onCreate()
        tokenRepository = TokenRepository(applicationContext)
    }
}