package com.example.android_repo_05.data.main.models

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("avatar_url")
    val profileImageUrl : String
)
