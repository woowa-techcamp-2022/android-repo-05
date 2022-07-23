package com.example.android_repo_05.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_05.data.network.ResponseState
import com.example.android_repo_05.data.login.models.TokenModel
import com.example.android_repo_05.data.login.TokenRepository
import kotlinx.coroutines.launch

class TokenViewModel(private val repository: TokenRepository) : ViewModel() {
    private var _tokenModel: MutableLiveData<ResponseState<TokenModel>> = MutableLiveData()
    val tokenModel: LiveData<ResponseState<TokenModel>> get() = _tokenModel

    fun getAccessTokenFromRemote(code: String) = viewModelScope.launch {
        _tokenModel.postValue(ResponseState.Loading())
        _tokenModel.postValue(repository.getAccessTokenFromRemote(code))
    }

    fun setAccessTokenToDataStore(accessToken: String) = viewModelScope.launch {
        repository.setAccessToken(accessToken = accessToken)
    }
}