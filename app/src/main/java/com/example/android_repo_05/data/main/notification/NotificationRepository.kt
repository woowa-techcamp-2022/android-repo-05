package com.example.android_repo_05.data.main.notification

import androidx.paging.PagingData
import com.example.android_repo_05.data.main.notification.models.NotificationModel
import com.example.android_repo_05.data.network.ResponseState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NotificationRepository {

    fun getNotificationFromRemote(): Flow<PagingData<NotificationModel>>

    suspend fun changeNotificationAsRead(url: String): ResponseState<String>

    fun handleResponse(response: Response<String>): ResponseState<String>

}