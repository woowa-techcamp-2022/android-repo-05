package com.example.android_repo_05.data.models

data class RepositoryModel(
    val id : Int,
    val language : String?,
    val repoName : String,
    val description : String?,
    val starCnt : String,
    val profileUrl : String,
    val userName : String
)
