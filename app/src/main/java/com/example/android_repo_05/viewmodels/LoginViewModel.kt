package com.example.android_repo_05.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_repo_05.data.model.LoginResponse
import com.example.android_repo_05.data.model.ResponseState
import com.example.android_repo_05.repositories.GithubApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: GithubApiRepository) : ViewModel() {
    private var _loginResponse: MutableLiveData<ResponseState<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<ResponseState<LoginResponse>> get() = _loginResponse

    fun getAccessTokenFromRemote(code: String) = viewModelScope.launch(Dispatchers.IO) {
        _loginResponse.postValue(ResponseState.Loading())
        _loginResponse.postValue(handleLoginResponse(repository.getAccessTokenFromRemote(code)))
    }

    private fun handleLoginResponse(response: Response<LoginResponse>): ResponseState<LoginResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        return ResponseState.Error(response.message())
    }

    fun getAccessTokenFromDataStore(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        repository.getAccessTokenFromDataStore(context).first().let { accessToken ->
            if (accessToken.isNotBlank()) {
                _loginResponse.postValue(ResponseState.Success(LoginResponse(accessToken, "", "")))
            } else {
                _loginResponse.postValue(ResponseState.Error("there is no access token"))
            }
        }
    }

    fun setAccessTokenToDataStore(context: Context, accessToken: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.setAccessTokenToDataStore(context, accessToken)
        }
}