package com.example.android_repo_05.di

import com.example.android_repo_05.data.network.GithubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideGitHubApiService() : GithubApiService {
        return GithubApiService.create()
    }
}