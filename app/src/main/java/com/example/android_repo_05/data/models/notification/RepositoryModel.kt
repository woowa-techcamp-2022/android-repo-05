package com.example.android_repo_05.data.models.notification

import com.google.gson.annotations.SerializedName

data class RepositoryModel(
    @SerializedName("full_name")
    val repositoryName: String,
    @SerializedName("owner")
    val owner: OwnerModel,
    @SerializedName("issue_comment_url")
    val commentsUrl: String,
)