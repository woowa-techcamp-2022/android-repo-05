package com.example.android_repo_05.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.android_repo_05.paging.NotificationPagingSource

class NotificationRepository(private val notificationPagingSource: NotificationPagingSource) {
    companion object {
        private var notificationRepo: NotificationRepository? = null

        fun getInstance(source: NotificationPagingSource): NotificationRepository {
            return notificationRepo ?: synchronized(this) {
                notificationRepo ?: NotificationRepository(source).also { notificationRepo = it }
            }
        }
    }

    fun getNotificationFromRemote() = Pager(PagingConfig(10)) { notificationPagingSource }.flow
}