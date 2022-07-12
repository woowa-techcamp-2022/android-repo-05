package com.example.android_repo_05.repositories

import com.example.android_repo_05.retrofit.GithubApiInstance

class GithubApiRepository {
    companion object {
        private var repo: GithubApiRepository? = null

        fun getInstance(): GithubApiRepository {
            return repo ?: synchronized(this) {
                repo ?: GithubApiRepository().also { repo = it }
            }
        }
    }

    suspend fun getAccessToken(code: String) =
        GithubApiInstance.retrofit.getAccessToken(accessCode = code)
}