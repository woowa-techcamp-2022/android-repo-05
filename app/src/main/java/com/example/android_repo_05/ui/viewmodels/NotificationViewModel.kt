package com.example.android_repo_05.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.android_repo_05.data.repositories.NotificationRepository

class NotificationViewModel(repository: NotificationRepository) : ViewModel() {
    val notificationList = repository.getNotificationFromRemote().cachedIn(viewModelScope)
}