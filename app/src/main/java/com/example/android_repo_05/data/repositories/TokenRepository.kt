package com.example.android_repo_05.data.repositories

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.android_repo_05.base.CustomApplication
import com.example.android_repo_05.data.datastore.dataStore
import com.example.android_repo_05.data.models.ResponseState
import com.example.android_repo_05.data.models.TokenModel
import com.example.android_repo_05.others.Constants
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
                if (result.accessToken.isNullOrBlank()) {
                    return ResponseState.Error("AccessToken 획득 실패")
                }
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }

    suspend fun setAccessToken(accessToken: String) {
        CustomApplication.instance.context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(Constants.prefKey)] = accessToken
        }
    }
}