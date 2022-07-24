package com.example.android_repo_05.data.main

import com.example.android_repo_05.data.main.models.UserProfileResponse
import com.example.android_repo_05.data.network.GithubApiInstance
import com.example.android_repo_05.data.network.GithubApiService
import com.example.android_repo_05.data.network.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ProfileImageRepositoryImpl @Inject constructor(
    private val service: GithubApiService
) : ProfileImageRepository {

    override suspend fun getProfileImageFromRemote() = withContext(Dispatchers.IO) {
        return@withContext try {
            handleLoginResponse(service.getProfileUrl())
        } catch (e: Exception) {
            ResponseState.Error(e.message ?: "error")
        }
    }

    override fun handleLoginResponse(response: Response<UserProfileResponse>): ResponseState<UserProfileResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }

}