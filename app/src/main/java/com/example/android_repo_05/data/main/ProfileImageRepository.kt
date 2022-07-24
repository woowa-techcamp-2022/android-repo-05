package com.example.android_repo_05.data.main

import com.example.android_repo_05.data.main.models.UserProfileResponse
import com.example.android_repo_05.data.network.ResponseState
import retrofit2.Response

interface ProfileImageRepository {

    suspend fun getProfileImageFromRemote(): ResponseState<UserProfileResponse>

    fun handleLoginResponse(response: Response<UserProfileResponse>): ResponseState<UserProfileResponse>

}