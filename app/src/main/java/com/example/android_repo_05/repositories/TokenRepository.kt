package com.example.android_repo_05.repositories

import com.example.android_repo_05.retrofit.GithubApiInstance


class TokenRepository {
    companion object {
        var tokenRepo: TokenRepository? = null
        fun getInstance(): TokenRepository {
            return tokenRepo ?: synchronized(this) {
                tokenRepo ?: TokenRepository().also { tokenRepo = it }
            }
        }
    }

    suspend fun getAccessTokenFromRemote(code: String) =
        GithubApiInstance.retrofit.getAccessToken(accessCode = code)

    // TODO: Repository 구조 정리 이후 이동
    suspend fun getUserInfoFromRemote() = GithubApiInstance.retrofit.getUserInfo()

    suspend fun getUserStarred() = GithubApiInstance.retrofit.getUserStarred()
}