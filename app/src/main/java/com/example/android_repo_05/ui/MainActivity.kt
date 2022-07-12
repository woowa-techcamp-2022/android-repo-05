package com.example.android_repo_05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

enum class MainTabType(val itemId : Int) {
    ISSUE(R.id.tabButton_issue),
    NOTIFICATION(R.id.tabButton_notification)
}