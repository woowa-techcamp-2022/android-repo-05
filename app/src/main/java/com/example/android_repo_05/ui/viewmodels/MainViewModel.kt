package com.example.android_repo_05.ui.viewmodels

import androidx.lifecycle.*
import com.example.android_repo_05.R
import com.example.android_repo_05.data.models.ResponseState
import com.example.android_repo_05.data.models.UserProfileResponse
import com.example.android_repo_05.data.repositories.ProfileImageRepository
import com.example.android_repo_05.ui.activities.MainTabType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: ProfileImageRepository) : ViewModel() {
    private val _currentTabFragment = MutableLiveData(MainTabType.ISSUE)
    val currentTabFragment: LiveData<MainTabType> = _currentTabFragment

    private val _profileUrl : MutableLiveData<ResponseState<UserProfileResponse>> = MutableLiveData()
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

    fun getProfileUrlFromRemote() = viewModelScope.launch(Dispatchers.IO) {
        _profileUrl.postValue(ResponseState.Loading())
        _profileUrl.postValue(handleLoginResponse(repository.getProfileImageFromRemote()))
    }

    private fun handleLoginResponse(response: Response<UserProfileResponse>): ResponseState<UserProfileResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }
}
