package com.example.android_repo_05.data.network

import com.example.android_repo_05.BuildConfig
import com.example.android_repo_05.data.login.models.TokenModel
import com.example.android_repo_05.data.main.issue.models.IssueResponse
import com.example.android_repo_05.data.main.models.UserProfileResponse
import com.example.android_repo_05.data.main.notification.models.CommentModel
import com.example.android_repo_05.data.main.notification.models.NotificationModel
import com.example.android_repo_05.data.profile.models.StarredModel
import com.example.android_repo_05.data.profile.models.UserModel
import com.example.android_repo_05.data.search.models.RepositoryResponse
import com.example.android_repo_05.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface GithubApiService {
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
        @Query("num") num: Int = Constants.ISSUE_PAGE_SIZE,
        @Query("state") state: String = "all",
        @Query("sort") sort: String = "updated"
    ): ArrayList<IssueResponse>

    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("page") page: Int,
        @Query("num") num: Int = Constants.SEARCH_PAGE_SIZE,
        @Query("q") query: String,
        @Query("sort") sort: String = "stars"
    ): RepositoryResponse

    @GET("user/starred")
    suspend fun getStarred(): Response<List<StarredModel>>

    @GET("user")
    suspend fun getProfileUrl(): Response<UserProfileResponse>

    @GET("notifications")
    suspend fun getNotification(
        @Query("per_page") resultNum: Int = Constants.NOTIFICATION_PAGE_SIZE,
        @Query("page") pageNum: Int
    ): List<NotificationModel>

    @GET
    suspend fun getComments(@Url url: String): CommentModel

    @PATCH
    suspend fun changeNotificationAsRead(@Url url: String): Response<String>

    companion object {
        fun create() : GithubApiService {
            val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

            return Retrofit.Builder()
                .baseUrl(Constants.githubApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(GithubApiService::class.java)
        }
    }
}