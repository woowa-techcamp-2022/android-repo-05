package com.example.android_repo_05.data.repositories

import com.example.android_repo_05.retrofit.GithubApiInstance

class ProfileImageRepository {
    companion object {
        private var profileRepo: ProfileImageRepository? = null

        fun getInstance(): ProfileImageRepository {
            return profileRepo ?: synchronized(this) {
                profileRepo ?: ProfileImageRepository().also { profileRepo = it }
            }
        }
    }

    suspend fun getProfileImageFromRemote() =
        GithubApiInstance.retrofit.getProfileUrl()

}