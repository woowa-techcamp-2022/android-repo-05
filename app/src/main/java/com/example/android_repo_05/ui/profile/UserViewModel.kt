package com.example.android_repo_05.ui.profile

import androidx.lifecycle.*
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.data.profile.models.UserModel
import com.example.android_repo_05.data.profile.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private var user: MutableLiveData<ResponseState<UserModel>> = MutableLiveData()
    private var starred: MutableLiveData<ResponseState<Int>> = MutableLiveData()
    val userData = MediatorLiveData<ResponseState<UserModel>>().apply {
        addSource(user) { value = setUserData() }
        addSource(starred) { value = setUserData() }
    }

    fun getUserInfoFromRemote() = viewModelScope.launch {
        user.postValue(ResponseState.Loading())
        user.postValue(repository.getUserInfoFromRemote())
    }

    fun getUserStarredFromRemote() = viewModelScope.launch {
        starred.postValue(ResponseState.Loading())
        starred.postValue(repository.getStarredFromRemote())
    }

    private fun setUserData(): ResponseState<UserModel> {
        return when {
            user.value is ResponseState.Success && starred.value is ResponseState.Success -> {
                ResponseState.Success(
                    user.value!!.data!!.apply { starredCount = starred.value!!.data!! }
                )
            }
            user.value is ResponseState.Error || starred.value is ResponseState.Error -> {
                ResponseState.Error("유저 정보 획득 실패")
            }
            else -> ResponseState.Loading()
        }
    }
}