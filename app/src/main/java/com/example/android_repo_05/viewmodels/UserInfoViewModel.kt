package com.example.android_repo_05.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_05.data.model.ResponseState
import com.example.android_repo_05.data.model.UserInfo
import com.example.android_repo_05.repositories.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class UserInfoViewModel(private val repository: TokenRepository) : ViewModel() {
    private var _userInfo: MutableLiveData<ResponseState<UserInfo>> = MutableLiveData()
    val userInfo: LiveData<ResponseState<UserInfo>> get() = _userInfo

    fun getUserInfoFromRemote() = viewModelScope.launch(Dispatchers.IO) {
        _userInfo.postValue(ResponseState.Loading())
        _userInfo.postValue(handleUserInfoResponse(repository.getUserInfoFromRemote()))
    }

    private fun handleUserInfoResponse(response: Response<UserInfo>): ResponseState<UserInfo> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }
}