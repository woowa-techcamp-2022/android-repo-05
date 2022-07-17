package com.example.android_repo_05.data.models

import com.google.gson.annotations.SerializedName

data class IssueResponse(
    @SerializedName("id")
    val id : Int,
    @SerializedName("state")
    val state : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("updated_at")
    val updated_at : String,
    @SerializedName("repository")
    val repository: Repository
)

data class Repository(
    @SerializedName("full_name")
    val full_name: String
)
