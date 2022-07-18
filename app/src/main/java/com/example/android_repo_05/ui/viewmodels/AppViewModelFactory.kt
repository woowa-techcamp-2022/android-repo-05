package com.example.android_repo_05.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_05.data.repositories.IssueRepository
import com.example.android_repo_05.data.repositories.ProfileImageRepository
import com.example.android_repo_05.data.repositories.TokenRepository
import com.example.android_repo_05.data.repositories.UserRepository

class AppViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TokenViewModel::class.java) -> {
                TokenViewModel(TokenRepository.getInstance()) as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(UserRepository.getInstance()) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(ProfileImageRepository.getInstance()) as T
            }
            modelClass.isAssignableFrom(IssueViewModel::class.java) -> {
                IssueViewModel(IssueRepository.getInstance()) as T
            }
            else -> {
                super.create(modelClass)
            }
        }
    }
}