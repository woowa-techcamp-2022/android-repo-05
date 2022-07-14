package com.example.android_repo_05.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.android_repo_05.data.datastore.dataStore
import com.example.android_repo_05.retrofit.GithubApiInstance
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class GithubApiRepository {
    companion object {
        private var repo: GithubApiRepository? = null

        fun getInstance(): GithubApiRepository {
            return repo ?: synchronized(this) {
                repo ?: GithubApiRepository().also { repo = it }
            }
        }
    }

    suspend fun getAccessTokenFromRemote(code: String) =
        GithubApiInstance.retrofit.getAccessToken(accessCode = code)

    suspend fun getAccessTokenFromDataStore(context: Context) = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[stringPreferencesKey("accessToken")] ?: ""
        }

    suspend fun setAccessTokenToDataStore(context: Context, accessToken: String) =
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey("accessToken")] = accessToken
        }

    suspend fun getUserInfoFromRemote(token: String) =
        GithubApiInstance.retrofit.getUserInfo("token $token")
}