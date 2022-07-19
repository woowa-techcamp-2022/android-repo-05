package com.example.android_repo_05.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.android_repo_05.data.models.ResponseState
import com.example.android_repo_05.paging.NotificationPagingSource
import com.example.android_repo_05.retrofit.GithubApiInstance.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NotificationRepository {
    companion object {
        private var notificationRepo: NotificationRepository? = null

        fun getInstance(): NotificationRepository {
            return notificationRepo ?: synchronized(this) {
                notificationRepo ?: NotificationRepository().also { notificationRepo = it }
            }
        }
    }

    fun getNotificationFromRemote() = Pager(PagingConfig(10)) { NotificationPagingSource() }.flow

    suspend fun changeNotificationAsRead(url: String) = withContext(Dispatchers.IO) {
        return@withContext handleResponse(retrofit.changeNotificationAsRead(url))
    }

    private fun handleResponse(response: Response<String>): ResponseState<String> {
        if (response.isSuccessful) {
            return ResponseState.Success(response.code().toString())
        }
        return ResponseState.Error(response.code().toString())
    }
}