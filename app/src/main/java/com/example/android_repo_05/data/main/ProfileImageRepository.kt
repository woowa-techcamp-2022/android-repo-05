package com.example.android_repo_05.data.main

import com.example.android_repo_05.data.main.models.UserProfileResponse
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.data.network.GithubApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProfileImageRepository {
    companion object {
        private var profileRepo: ProfileImageRepository? = null

        fun getInstance(): ProfileImageRepository {
            return profileRepo ?: synchronized(this) {
                profileRepo ?: ProfileImageRepository().also { profileRepo = it }
            }
        }
    }

    suspend fun getProfileImageFromRemote() = withContext(Dispatchers.IO) {
        return@withContext try {
            handleLoginResponse(GithubApiInstance.retrofit.getProfileUrl())
        } catch (e: Exception) {
            ResponseState.Error(e.message ?: "error")
        }
    }

    private fun handleLoginResponse(response: Response<UserProfileResponse>): ResponseState<UserProfileResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }
}