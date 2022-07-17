package com.example.android_repo_05.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_05.data.models.ResponseState
import com.example.android_repo_05.data.models.UserModel
import com.example.android_repo_05.data.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private var _userModel: MutableLiveData<ResponseState<UserModel>> = MutableLiveData()
    val userModel: LiveData<ResponseState<UserModel>> get() = _userModel
    private var _starredCount: MutableLiveData<ResponseState<Int>> = MutableLiveData()
    val starredCount: LiveData<ResponseState<Int>> get() = _starredCount

    fun getUserInfoFromRemote() = viewModelScope.launch {
        _userModel.postValue(ResponseState.Loading())
        _userModel.postValue(repository.getUserInfoFromRemote())
    }

    fun getUserStarredFromRemote() = viewModelScope.launch {
        _starredCount.postValue(ResponseState.Loading())
        _starredCount.postValue(repository.getStarredFromRemote())
    }
}