package com.example.android_repo_05.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.R
import com.example.android_repo_05.data.model.LoginResponse
import com.example.android_repo_05.data.model.ResponseState
import com.example.android_repo_05.databinding.ActivityProfileBinding
import com.example.android_repo_05.repositories.GithubApiRepository
import com.example.android_repo_05.viewmodels.AppViewModelFactory
import com.example.android_repo_05.viewmodels.LoginViewModel
import com.example.android_repo_05.viewmodels.UserInfoViewModel

class ProfileActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private val loginViewModel by lazy {
        ViewModelProvider(
            this,
            AppViewModelFactory(GithubApiRepository.getInstance())
        )[LoginViewModel::class.java]
    }
    private val userInfoViewModel by lazy {
        ViewModelProvider(
            this,
            AppViewModelFactory(GithubApiRepository.getInstance())
        )[UserInfoViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setObservers()
        getAccessToken()
    }

    private fun setObservers() {
        loginViewModel.loginResponse.observe(this) { responseState ->
            when (responseState) {
                is ResponseState.Success -> getUserInfo(responseState.data)
                is ResponseState.Error -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                is ResponseState.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }
        }

        userInfoViewModel.userInfo.observe(this) { responseState ->
            when (responseState) {
                is ResponseState.Success -> Toast.makeText(this, responseState.data?.displayName, Toast.LENGTH_SHORT).show()
                is ResponseState.Error -> Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                is ResponseState.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserInfo(data: LoginResponse?) {
        data?.let { loginResponse ->
            userInfoViewModel.getUserInfoFromRemote(loginResponse.accessToken)
        }
    }

    private fun getAccessToken() {
        loginViewModel.getAccessTokenFromDataStore(this)
    }
}