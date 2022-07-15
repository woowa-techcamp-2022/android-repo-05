package com.example.android_repo_05.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException

class TokenRepository(private val context: Context) {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val tokenPreferenceKey = stringPreferencesKey("ACCESSTOKEN")

    val accessTokenFlow: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            var accessToken = preferences[tokenPreferenceKey] ?: ""
            accessToken
        }.flowOn(Dispatchers.IO)

    suspend fun setAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenPreferenceKey] = accessToken
        }
    }
}