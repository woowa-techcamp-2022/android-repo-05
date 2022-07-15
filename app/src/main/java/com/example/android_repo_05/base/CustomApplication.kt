package com.example.android_repo_05.base

import android.app.Application
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

class CustomApplication : Application() {
    companion object {
        lateinit var instance: Context

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "accessToken")
        private val tokenPreferenceKey = stringPreferencesKey("ACCESSTOKEN")
        //TODO : DataStore 관련 어떻게 할지 결정하기 (application?, repository?)
        lateinit var accessTokenFlow: Flow<String>
        suspend fun setAccessToken(context: Context = instance, accessToken: String) {
            context.dataStore.edit { preferences ->
                preferences[tokenPreferenceKey] = accessToken
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext
        accessTokenFlow = instance.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                var accessToken = preferences[tokenPreferenceKey] ?: ""
                accessToken
            }.flowOn(Dispatchers.IO)
    }
}