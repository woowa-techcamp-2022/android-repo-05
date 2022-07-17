package com.example.android_repo_05.retrofit

import com.example.android_repo_05.BuildConfig
import com.example.android_repo_05.data.models.IssueResponse
import com.example.android_repo_05.data.models.TokenModel
import com.example.android_repo_05.data.models.UserModel
import com.example.android_repo_05.data.models.UserProfileResponse
import com.example.android_repo_05.data.models.StarredModel
import com.example.android_repo_05.others.Constants
import retrofit2.Response
import retrofit2.http.*

interface GithubApi {
    @FormUrlEncoded
    @POST(Constants.authBaseUrl + "/login/oauth/access_token")
    @Headers("Accept: application/json")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String = BuildConfig.GITHUB_CLIENT_ID,
        @Field("client_secret") clientSecrets: String = BuildConfig.GITHUB_CLIENT_SECRETS,
        @Field("code") accessCode: String
    ): Response<TokenModel>

    @GET("user")
    suspend fun getUserInfo(): Response<UserModel>

    @GET("user/issues")
    suspend fun getIssues(
        @Query("page") page : Int,
        @Query("num") num : Int = 10,
        @Query("state") state : String = "all"
    ): ArrayList<IssueResponse>

    @GET("user/starred")
    suspend fun getUserStarred(): Response<List<StarredModel>>

    @GET("user")
    suspend fun getProfileUrl() : Response<UserProfileResponse>
}