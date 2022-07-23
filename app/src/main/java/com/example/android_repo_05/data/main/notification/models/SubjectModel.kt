package com.example.android_repo_05.data.main.notification.models

import com.google.gson.annotations.SerializedName

data class SubjectModel(
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
)
