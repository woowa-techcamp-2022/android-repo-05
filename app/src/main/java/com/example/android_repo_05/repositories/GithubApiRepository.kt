package com.example.android_repo_05.repositories

import com.example.android_repo_05.retrofit.GithubApiInstance

class GithubApiRepository {
    companion object {
        var githubApiRepo: GithubApiRepository? = null

        fun getInstance(): GithubApiRepository {
            return githubApiRepo ?: synchronized(this) {
                githubApiRepo ?: GithubApiRepository().also { githubApiRepo = it }
            }
        }
    }

    suspend fun getAccessTokenFromRemote(code: String) =
        GithubApiInstance.retrofit.getAccessToken(accessCode = code)

    suspend fun getUserInfoFromRemote() =
        GithubApiInstance.retrofit.getUserInfo()
}