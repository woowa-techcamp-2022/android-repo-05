package com.example.android_repo_05.repositories

import com.example.android_repo_05.retrofit.GithubApiInstance

class ProfileImageRepository {
    companion object {
        var profileRepo: ProfileImageRepository? = null

        fun getInstance(): ProfileImageRepository {
            return profileRepo ?: synchronized(this) {
                profileRepo ?: ProfileImageRepository().also { profileRepo = it }
            }
        }
    }

    suspend fun getProfileImageFromRemote() =
        GithubApiInstance.retrofit.getProfileUrl()

}