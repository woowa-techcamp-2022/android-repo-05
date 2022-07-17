package com.example.android_repo_05.data.repositories

import com.example.android_repo_05.retrofit.GithubApiInstance

class UserRepository {
    companion object {
        private var userRepo: UserRepository? = null

        fun getInstance(): UserRepository {
            return userRepo ?: synchronized(this) {
                userRepo ?: UserRepository().also { userRepo = it }
            }
        }
    }

    suspend fun getUserInfoFromRemote() = GithubApiInstance.retrofit.getUserInfo()

    suspend fun getStarredFromRemote() = GithubApiInstance.retrofit.getUserStarred()
}