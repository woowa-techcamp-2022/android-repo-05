package com.example.android_repo_05.data.models

import com.example.android_repo_05.others.Utils
import com.google.gson.annotations.SerializedName

data class IssueResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("state")
    val state: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("repository")
    val repository: Repository,
    @SerializedName("number")
    val issueNumber: Int
) {
    val elapsedTime: String get() = Utils.calculateElapsedTime(updatedAt)
}

data class Repository(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("name")
    val repoName: String
)