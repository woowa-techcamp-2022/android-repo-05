package com.example.android_repo_05.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_05.R
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.data.main.models.UserProfileResponse
import com.example.android_repo_05.data.main.ProfileImageRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProfileImageRepository) : ViewModel() {
    private val _currentTabFragment = MutableLiveData(MainTabType.ISSUE)
    val currentTabFragment: LiveData<MainTabType> = _currentTabFragment

    private val _profileUrl: MutableLiveData<ResponseState<UserProfileResponse>> = MutableLiveData()
    val profileUrl: LiveData<ResponseState<UserProfileResponse>> = _profileUrl

    private fun getMainTabType(itemId: Int): MainTabType =
        when (itemId) {
            R.id.tab_btn_issue -> MainTabType.ISSUE
            R.id.tab_btn_notification -> MainTabType.NOTIFICATION
            else -> throw IllegalArgumentException()
        }

    fun setCurrentTab(itemId: Int): Boolean {
        changeCurrentTab(getMainTabType(itemId))
        return true
    }

    private fun changeCurrentTab(mainTabType: MainTabType) {
        if (currentTabFragment.value != mainTabType) _currentTabFragment.value = mainTabType
    }

    fun getProfileUrlFromRemote() = viewModelScope.launch {
        _profileUrl.postValue(ResponseState.Loading())
        _profileUrl.postValue(repository.getProfileImageFromRemote())
    }
}
