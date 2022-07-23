package com.example.android_repo_05.data.network

import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.android_repo_05.CustomApplication
import com.example.android_repo_05.data.datastore.dataStore
import com.example.android_repo_05.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking(Dispatchers.IO) {
            CustomApplication.instance.context.dataStore.data
                .catch { exception ->
                    if (exception is IOException) emit(emptyPreferences()) else throw exception
                }.map { preferences ->
                    preferences[stringPreferencesKey(Constants.prefKey)] ?: ""
                }.first()
        }
        val req = chain.request().newBuilder()
            .addHeader("Accept", "application/vnd.github+json")
            .addHeader("Authorization", "token $accessToken")
            .build()
        return chain.proceed(req)
    }
}