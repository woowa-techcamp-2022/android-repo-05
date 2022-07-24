package com.example.android_repo_05.data.login

import com.example.android_repo_05.data.login.models.TokenModel
import com.example.android_repo_05.data.network.ResponseState
import retrofit2.Response

interface TokenRepository {

    suspend fun getAccessTokenFromRemote(code: String): ResponseState<TokenModel>

    fun handleTokenResponse(response: Response<TokenModel>): ResponseState<TokenModel>

    suspend fun setAccessToken(accessToken: String)

}