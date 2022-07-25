package com.example.android_repo_05.data.profile

import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.data.profile.models.StarredModel
import com.example.android_repo_05.data.profile.models.UserModel
import retrofit2.Response

interface UserRepository {

    suspend fun getUserInfoFromRemote() : ResponseState<UserModel>

    suspend fun getStarredFromRemote(): ResponseState<Int>

    fun handleUserResponse(response: Response<UserModel>): ResponseState<UserModel>

    fun handleStarredResponse(response: Response<List<StarredModel>>): ResponseState<Int>

}