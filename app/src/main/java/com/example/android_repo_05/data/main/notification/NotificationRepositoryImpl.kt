package com.example.android_repo_05.data.main.notification

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_repo_05.data.main.notification.models.NotificationModel
import com.example.android_repo_05.data.main.notification.paging.NotificationPagingSource
import com.example.android_repo_05.data.network.GithubApiService
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.utils.Constants.NOTIFICATION_PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val service: GithubApiService
) : NotificationRepository {

    override fun getNotificationFromRemote(): Flow<PagingData<NotificationModel>> =
        Pager(PagingConfig(NOTIFICATION_PAGE_SIZE)) { NotificationPagingSource(service) }.flow

    override suspend fun changeNotificationAsRead(url: String) = withContext(Dispatchers.IO) {
        return@withContext try {
            handleResponse(service.changeNotificationAsRead(url))
        } catch (e: Exception) {
            ResponseState.Error(e.message ?: "error")
        }
    }

    override fun handleResponse(response: Response<String>): ResponseState<String> {
        if (response.isSuccessful) {
            return ResponseState.Success(response.code().toString())
        }
        return ResponseState.Error(response.code().toString())
    }

}