package com.example.android_repo_05.retrofit

import com.example.android_repo_05.BuildConfig
import com.example.android_repo_05.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface GithubApi {
    @FormUrlEncoded
    @POST("/login/oauth/access_token")
    @Headers("Accept: application/json")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String = BuildConfig.GITHUB_CLIENT_ID,
        @Field("client_secret") clientSecrets: String = BuildConfig.GITHUB_CLIENT_SECRETS,
        @Field("code") accessCode: String
    ): Response<LoginResponse>
}