package com.example.android_repo_05.data.profile.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("avatar_url")
    val profileImageUrl: String,
    @SerializedName("name")
    val displayName: String?,
    @SerializedName("login")
    val userName: String,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("blog")
    val link: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("followers")
    val followersCount: Int,
    @SerializedName("following")
    val followingCount: Int,
    @SerializedName("public_repos")
    val publicReposCount: Int,
    @SerializedName("total_private_repos")
    val privateReposCount: Int
) {
    val totalReposCount: Int get() = publicReposCount + privateReposCount
    var starredCount: Int = 0
}
