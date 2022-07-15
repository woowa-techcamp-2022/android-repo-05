package com.example.android_repo_05.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.repositories.ProfileImageRepository
import com.example.android_repo_05.repositories.TokenRepository

class AppViewModelFactory(
    private val githubApiRepository: TokenRepository? = TokenRepository.tokenRepo,
    private val profileImageRepository: ProfileImageRepository? = ProfileImageRepository.profileRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(githubApiRepository!!) as T
            }
            modelClass.isAssignableFrom(UserInfoViewModel::class.java) -> {
                UserInfoViewModel(githubApiRepository!!) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(profileImageRepository!!) as T
            }
            else -> {
                super.create(modelClass)
            }
        }
    }
}