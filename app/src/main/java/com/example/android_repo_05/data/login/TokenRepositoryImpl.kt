package com.example.android_repo_05.data.login

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.android_repo_05.CustomApplication
import com.example.android_repo_05.data.datastore.dataStore
import com.example.android_repo_05.data.login.models.TokenModel
import com.example.android_repo_05.data.network.GithubApiService
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val service: GithubApiService
) : TokenRepository {

    override suspend fun getAccessTokenFromRemote(code: String) = withContext(Dispatchers.IO) {
        return@withContext try {
            handleTokenResponse(service.getAccessToken(accessCode = code))
        } catch (e: Exception) {
            ResponseState.Error(e.message ?: "error")
        }
    }

    override fun handleTokenResponse(response: Response<TokenModel>): ResponseState<TokenModel> {
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

    override suspend fun setAccessToken(accessToken: String) {
        CustomApplication.instance.context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(Constants.prefKey)] = accessToken
        }
    }

}