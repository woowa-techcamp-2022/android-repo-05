package com.example.android_repo_05.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_05.data.model.ResponseState
import com.example.android_repo_05.data.model.UserInfo
import com.example.android_repo_05.data.model.UserStarred
import com.example.android_repo_05.repositories.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class UserInfoViewModel(private val repository: TokenRepository) : ViewModel() {
    private var _userInfo: MutableLiveData<ResponseState<UserInfo>> = MutableLiveData()
    val userInfo: LiveData<ResponseState<UserInfo>> get() = _userInfo
    private var _starredCount: MutableLiveData<ResponseState<Int>> = MutableLiveData()
    val starredCount: LiveData<ResponseState<Int>> get() = _starredCount

    fun getUserInfoFromRemote() = viewModelScope.launch(Dispatchers.IO) {
        _userInfo.postValue(ResponseState.Loading())
        _userInfo.postValue(handleUserInfoResponse(repository.getUserInfoFromRemote()))
    }

    fun getUserStarredFromRemote() = viewModelScope.launch(Dispatchers.IO) {
        _starredCount.postValue(ResponseState.Loading())
        _starredCount.postValue(handleUserStarredResponse(repository.getUserStarred()))
    }

    private fun handleUserInfoResponse(response: Response<UserInfo>): ResponseState<UserInfo> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }

    private fun handleUserStarredResponse(response: Response<List<UserStarred>>): ResponseState<Int> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result.size)
            }
        }
        return ResponseState.Error("${response.code()}")
    }
}