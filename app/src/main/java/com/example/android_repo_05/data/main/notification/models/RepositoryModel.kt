package com.example.android_repo_05.data.main.notification.models

import com.google.gson.annotations.SerializedName

data class RepositoryModel(
    @SerializedName("full_name")
    val repositoryName: String,
    @SerializedName("owner")
    val owner: OwnerModel
)