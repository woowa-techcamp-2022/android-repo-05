package com.example.android_repo_05.data.main.notification.models

import com.example.android_repo_05.utils.TimeUtils
import com.google.gson.annotations.SerializedName

data class NotificationModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("repository")
    val repository: RepositoryModel,
    @SerializedName("subject")
    val subject: SubjectModel,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("unread")
    val unread: Boolean,
    @SerializedName("url")
    val url: String
) {
    val elapsedTime: String get() = TimeUtils.calculateElapsedTime(updatedAt)
    var commentCount: Int = 0
    var number: String = ""
}