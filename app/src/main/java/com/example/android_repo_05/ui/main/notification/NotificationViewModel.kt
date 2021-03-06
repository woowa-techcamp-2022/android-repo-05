package com.example.android_repo_05.ui.main.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.data.main.notification.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {
    private val _removeItemFlow = MutableStateFlow(mutableListOf<String>())
    private val removeItemFlow: Flow<MutableList<String>> get() = _removeItemFlow

    val notificationList = repository.getNotificationFromRemote()
        .cachedIn(viewModelScope)
        .combine(removeItemFlow) { list, removed ->
            list.filter { it.id !in removed }
        }

    private var _changeResponse: MutableLiveData<ResponseState<String>> = MutableLiveData()
    val changeResponse: LiveData<ResponseState<String>> get() = _changeResponse

    fun changeNotificationAsRead(url: String) = viewModelScope.launch {
        _changeResponse.postValue(ResponseState.Loading())
        _changeResponse.postValue(repository.changeNotificationAsRead(url))
    }

    fun removeItem(itemId: String) {
        _removeItemFlow.value.add(itemId)
    }

    fun restoreItem() {
        _removeItemFlow.value.removeLast()
    }
}