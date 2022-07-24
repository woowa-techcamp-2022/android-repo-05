package com.example.android_repo_05.di

import com.example.android_repo_05.data.login.TokenRepository
import com.example.android_repo_05.data.login.TokenRepositoryImpl
import com.example.android_repo_05.data.main.ProfileImageRepository
import com.example.android_repo_05.data.main.ProfileImageRepositoryImpl
import com.example.android_repo_05.data.main.issue.IssueRepository
import com.example.android_repo_05.data.main.issue.IssueRepositoryImpl
import com.example.android_repo_05.data.main.notification.NotificationRepository
import com.example.android_repo_05.data.main.notification.NotificationRepositoryImpl
import com.example.android_repo_05.data.network.GithubApiService
import com.example.android_repo_05.data.profile.UserRepository
import com.example.android_repo_05.data.profile.UserRepositoryImpl
import com.example.android_repo_05.data.search.RepositoryRepository
import com.example.android_repo_05.data.search.RepositoryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepositoryRepository(
        githubApiService: GithubApiService
    ): RepositoryRepository {
        return RepositoryRepositoryImpl(githubApiService)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        githubApiService: GithubApiService
    ): UserRepository {
        return UserRepositoryImpl(githubApiService)
    }

    @Singleton
    @Provides
    fun provideProfileImageRepository(
        githubApiService: GithubApiService
    ): ProfileImageRepository {
        return ProfileImageRepositoryImpl(githubApiService)
    }

    @Singleton
    @Provides
    fun provideNotificationRepository(
        githubApiService: GithubApiService
    ): NotificationRepository {
        return NotificationRepositoryImpl(githubApiService)
    }

    @Singleton
    @Provides
    fun provideIssueRepository(
        githubApiService: GithubApiService
    ): IssueRepository {
        return IssueRepositoryImpl(githubApiService)
    }

    @Singleton
    @Provides
    fun provideTokenRepository(
        githubApiService: GithubApiService
    ): TokenRepository {
        return TokenRepositoryImpl(githubApiService)
    }

}