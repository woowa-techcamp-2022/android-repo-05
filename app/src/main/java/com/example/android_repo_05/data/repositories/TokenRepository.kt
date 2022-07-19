package com.example.android_repo_05.data.repositories

import android.util.Log
import com.example.android_repo_05.data.models.ResponseState
import com.example.android_repo_05.data.models.TokenModel
import com.example.android_repo_05.retrofit.GithubApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


class TokenRepository {
    companion object {
        private var tokenRepo: TokenRepository? = null

        fun getInstance(): TokenRepository {
            return tokenRepo ?: synchronized(this) {
                tokenRepo ?: TokenRepository().also { tokenRepo = it }
            }
        }
    }

    suspend fun getAccessTokenFromRemote(code: String) = withContext(Dispatchers.IO) {
        val response = GithubApiInstance.retrofit.getAccessToken(accessCode = code)
        return@withContext handleTokenResponse(response)
    }

    private fun handleTokenResponse(response: Response<TokenModel>): ResponseState<TokenModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                if(result.accessToken.isNullOrBlank()) {
                    return ResponseState.Error("AccessToken 획득 실패")
                }
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }
}