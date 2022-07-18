package com.example.android_repo_05.data.models.notification

import com.google.gson.annotations.SerializedName

data class OwnerModel(
    @SerializedName("avatar_url")
    val orgImageUrl: String
)
