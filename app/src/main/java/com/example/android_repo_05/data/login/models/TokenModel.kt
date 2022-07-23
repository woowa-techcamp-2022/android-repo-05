package com.example.android_repo_05.data.login.models

import com.google.gson.annotations.SerializedName

data class TokenModel(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("scope")
    val scope: String?,
    @SerializedName("token_type")
    val tokenType: String?
)