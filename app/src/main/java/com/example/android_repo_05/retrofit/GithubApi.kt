package com.example.android_repo_05.retrofit

import com.example.android_repo_05.BuildConfig
import com.example.android_repo_05.data.models.*
import com.example.android_repo_05.data.models.notification.CommentModel
import com.example.android_repo_05.data.models.notification.NotificationModel
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
    suspend fun getUser(): Response<UserModel>

    @GET("user/issues")
    suspend fun getIssues(
        @Query("page") page: Int,
        @Query("num") num: Int = 10,
        @Query("state") state: String = "all",
        @Query("sort") sort: String = "updated"
    ): ArrayList<IssueResponse>

    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("page") page: Int,
        @Query("num") num: Int = 10,
        @Query("q") query : String,
        @Query("sort") sort: String = "stars"
    ): RepositoryResponse

    @GET("user/starred")
    suspend fun getStarred(): Response<List<StarredModel>>

    @GET("user")
    suspend fun getProfileUrl(): Response<UserProfileResponse>

    @GET("notifications")
    suspend fun getNotification(
        @Query("per_page") resultNum: Int = Constants.NETWORK_PAGE_SIZE,
        @Query("page") pageNum: Int
    ): List<NotificationModel>

    @GET
    suspend fun getIssueComments(@Url url: String): List<CommentModel>

    @PATCH
    suspend fun changeNotificationAsRead(@Url url: String): Response<String>
}