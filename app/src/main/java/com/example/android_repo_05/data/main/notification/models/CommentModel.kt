package com.example.android_repo_05.data.main.notification.models

import com.google.gson.annotations.SerializedName

data class CommentModel(
    @SerializedName("number")
    val number: Int?,
    @SerializedName("comments")
    val comments: Int
)
