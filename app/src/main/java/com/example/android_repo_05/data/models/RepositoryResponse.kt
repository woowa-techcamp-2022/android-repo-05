package com.example.android_repo_05.data.models

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("items")
    val items: List<Item>
) {
    data class Item(
        @SerializedName("id")
        val id: Int,
        @SerializedName("language")
        val language: String?,
        @SerializedName("name")
        val repoName: String,
        @SerializedName("owner")
        val owner: Owner,
        @SerializedName("description")
        val description: String?,
        @SerializedName("stargazers_count")
        val stargazersCount: Int
    ) {
        data class Owner(
            @SerializedName("avatar_url")
            val profileUrl: String,
            @SerializedName("login")
            val userName: String
        )

        fun unitConversion(starCnt: Int): String {
            return if (starCnt >= 1000) "${starCnt / 1000}.${(starCnt % 1000) / 100}k"
            else starCnt.toString()
        }

        fun mapperToRepositoryModel(): RepositoryModel {
            return RepositoryModel(
                id = id,
                description = description ?: "",
                userName = owner.userName,
                language = language ?: "",
                repoName = repoName,
                profileUrl = owner.profileUrl,
                starCnt = unitConversion(stargazersCount)
            )
        }
    }
}