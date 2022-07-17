package com.example.android_repo_05.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_05.base.CustomApplication.Companion.setAccessToken
import com.example.android_repo_05.data.models.TokenModel
import com.example.android_repo_05.data.models.ResponseState
import com.example.android_repo_05.data.repositories.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class TokenViewModel(private val repository: TokenRepository) : ViewModel() {
    private var _tokenModel: MutableLiveData<ResponseState<TokenModel>> = MutableLiveData()
    val tokenModel: LiveData<ResponseState<TokenModel>> get() = _tokenModel

    fun getAccessTokenFromRemote(code: String) = viewModelScope.launch(Dispatchers.IO) {
        _tokenModel.postValue(ResponseState.Loading())
        _tokenModel.postValue(handleLoginResponse(repository.getAccessTokenFromRemote(code)))
    }

    private fun handleLoginResponse(response: Response<TokenModel>): ResponseState<TokenModel> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }

    fun setAccessTokenToDataStore(accessToken: String) =
        viewModelScope.launch(Dispatchers.IO) {
            setAccessToken(accessToken = accessToken)
        }
}