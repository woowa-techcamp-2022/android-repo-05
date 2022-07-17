package com.example.android_repo_05.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_05.data.models.ResponseState
import com.example.android_repo_05.data.models.UserModel
import com.example.android_repo_05.data.models.StarredModel
import com.example.android_repo_05.data.repositories.TokenRepository
import com.example.android_repo_05.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private var _userModel: MutableLiveData<ResponseState<UserModel>> = MutableLiveData()
    val userModel: LiveData<ResponseState<UserModel>> get() = _userModel
    private var _starredCount: MutableLiveData<ResponseState<Int>> = MutableLiveData()
    val starredCount: LiveData<ResponseState<Int>> get() = _starredCount

    fun getUserInfoFromRemote() = viewModelScope.launch(Dispatchers.IO) {
        _userModel.postValue(ResponseState.Loading())
        _userModel.postValue(handleUserInfoResponse(repository.getUserInfoFromRemote()))
    }

    fun getUserStarredFromRemote() = viewModelScope.launch(Dispatchers.IO) {
        _starredCount.postValue(ResponseState.Loading())
        _starredCount.postValue(handleUserStarredResponse(repository.getStarredFromRemote()))
    }

    private fun handleUserInfoResponse(response: Response<UserModel>): ResponseState<UserModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }

    private fun handleUserStarredResponse(response: Response<List<StarredModel>>): ResponseState<Int> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result.size)
            }
        }
        return ResponseState.Error("${response.code()}")
    }
}